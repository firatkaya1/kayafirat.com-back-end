package com.firatkaya.service.Impl;

import com.firatkaya.entity.User;
import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.service.OauthService;
import com.firatkaya.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OauthServiceImpl implements OauthService {

    private final RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public OauthServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String oAuthGithubUserAuthenticate(String code) throws Exception {

        String access_token = oAuthGithubUserAccessToken(code);

        JSONObject jsonObject = new JSONObject(getGithubUser(access_token));
        return saveAuthUser(jsonObject.get("email").toString(),jsonObject.get("login").toString(),createRandomPassword());

    }

    private String oAuthGithubUserAccessToken(String code) {
        String url = "https://github.com/login/oauth/access_token?client_id=1766cc6e638422eeaa65&client_secret=7cc726ac8eed0d4384bb1e937ce5a8b44059684a&code="+code;
        return restTemplate.postForEntity(url,null,String.class).getBody().substring(13,53);

    }

    private Map getGithubUser(String accessToken) {
        String url = "https://api.github.com/user";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);


        return restTemplate.exchange(url, HttpMethod.GET,entity, Map.class).getBody();
    }


    private String createRandomPassword() {
        String[] alphabet = {"a","b","c","d","e","f","g","h","l","A","B","C","D","E","F","G","L","1","2","3","4","5","6","7","8","9","0"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i<=15;i++) {
            sb.append(alphabet[(int) (Math.random() * (25) + 0)]);
        }
        return sb.toString();
    }

    private String saveAuthUser(String email, String username,String password) throws Exception {
        User user = new User();
        user.setUserName(username);
        user.setUserEmail(email);
        user.setVerification(true);
        user.setUserPassword(password);
        user.setUserBirthdayDate("2000-01-01 01:00:00");
        userService.saveUser(user);
        return userService.authenticateUser(new AuthenticationRequest(email,password));
    }


}
