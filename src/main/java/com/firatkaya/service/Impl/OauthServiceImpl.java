package com.firatkaya.service.Impl;

import com.firatkaya.entity.User;
import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.service.OauthService;
import com.firatkaya.service.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OauthServiceImpl implements OauthService {

    private final static String GITHUB_USER_ROOT_URI = "";
    private final static String GITHUB_OAUTH_ROOT_URI = "";
    private final static String GITHUB_CLIENT_ID = "";
    private final static String GITHUB_CLIENT_SECRET = "";

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Environment env;



    public String oAuthGithubUserAuthenticate(String code) throws Exception {

        String accessToken = oAuthGithubUserAccessToken(code);

        JSONObject jsonObject = new JSONObject(getGithubUser(accessToken));
        return saveAuthUser(jsonObject.get("email").toString(),jsonObject.get("login").toString(),createRandomPassword());

    }

    public String oAuthLinkedinUserAuthenticate(String code) throws Exception {
        String accessToken = oAuthLinkedinAccessToken(code);
        Map userMap = getLinkedinUserName(accessToken);
        String val = getLinkedinUserEmail(accessToken).toString();
        String email = val.substring(val.indexOf("ss=")+3,val.lastIndexOf("}, "));
        String username = userMap.get("localizedFirstName").toString().toLowerCase() + userMap.get("localizedLastName").toString().toLowerCase() + ((int) (Math.random() * (10000) + 1000));

        return saveAuthUser(email,username,createRandomPassword());

    }

    private String oAuthLinkedinAccessToken(String code){
        String url = env.getProperty("oauth2.linkedin.root-uri")+"code="+code+"&client_id="+env.getProperty("oauth2.linkedin.client-id")+"&client_secret="+env.getProperty("oauth2.linkedin.client-secret")+"&redirect_uri="+env.getProperty("oauth2.linkedin.redirect-uri")+"&grant_type=authorization_code";
        return restTemplate.postForEntity(url,null,Map.class).getBody().get("access_token").toString();

    }

    private Map getLinkedinUserEmail(String accessToken) {
        String url = "https://api.linkedin.com/v2/clientAwareMemberHandles?q=members&projection=(elements*(primary,type,handle~))";
        return restTemplate.exchange(url, HttpMethod.GET,prepareHTTPEntity(accessToken), Map.class).getBody();

    }

    private Map getLinkedinUserName(String accessToken) {
        String url = "https://api.linkedin.com/v2/me";
        return restTemplate.exchange(url, HttpMethod.GET,prepareHTTPEntity(accessToken), Map.class).getBody();
    }

    private String oAuthGithubUserAccessToken(String code) {
        String url = env.getProperty("oauth2.github.user-root-uri")+"/access_token?client_id="+GITHUB_CLIENT_ID+"&client_secret="+GITHUB_CLIENT_SECRET+"&code="+code;
        return restTemplate.postForEntity(url,null,String.class).getBody().substring(13,53);
    }

    private Map getGithubUser(String accessToken) {
        return restTemplate.exchange(env.getProperty("oauth2.github.user-root-uri"), HttpMethod.GET,prepareHTTPEntity(accessToken), Map.class).getBody();
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

        if(userRepository.existsByUserEmail(email)){
            User user = userRepository.findByUserEmail(email);
            return userService.authenticateUser(new AuthenticationRequest(user.getUserEmail(),user.getUserPassword()));
        }else {
            User user = new User();
            user.setUserName(username);
            user.setUserEmail(email);
            user.setVerification(true);
            user.setUserPassword(password);
            user.setUserBirthdayDate("2000-01-01 01:00:00");
            userService.saveUser(user);

        }
        return userService.authenticateUser(new AuthenticationRequest(email,password));

    }

    private HttpEntity<String> prepareHTTPEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent","blog.kayafirat.com");
        headers.setBearerAuth (accessToken);
        return new HttpEntity<>("parameters", headers);
    }


}
