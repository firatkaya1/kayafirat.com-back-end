package com.firatkaya.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.util.JwtUtil;
import com.firatkaya.validation.constraint.ExistsEmail;
import com.firatkaya.validation.constraint.ExistsId;
import com.firatkaya.validation.constraint.ExistsUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.firatkaya.entity.User;
import com.firatkaya.entity.UserPermissions;
import com.firatkaya.entity.UserProfile;
import com.firatkaya.exceptions.customExceptions.MailException;
import com.firatkaya.exceptions.customExceptions.UserEmailAlreadyExistsException;
import com.firatkaya.exceptions.customExceptions.UserNameAlreadyExistsException;
import com.firatkaya.exceptions.customExceptions.UserEmailNotFoundException;
import com.firatkaya.model.projection.UserExceptr;
import com.firatkaya.repository.CommentRepository;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.service.EmailService;
import com.firatkaya.service.UserService;


@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final EmailService emailService;

    @Autowired
    private Environment env;

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImp(RestTemplateBuilder restTemplateBuilder,
                          UserRepository userRepository,
                          CommentRepository commentRepository,
                          EmailService emailService ) {

        this.restTemplate = restTemplateBuilder.build();
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.emailService = emailService;
    }

    @Override
    @Cacheable(cacheNames = "User", key = "#email")
    public User getUserByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Override
    @Cacheable(cacheNames = "User", key = "#username")
    public User getUserByUsername(@ExistsUsername String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public User saveUser(User user) {
        if(user.getUserEmail() == null) {
            throw new UserEmailNotFoundException("sd");
        }
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new UserEmailAlreadyExistsException(user.getUserEmail());

        } else if (userRepository.existsByUserName(user.getUserName())) {
            throw new UserNameAlreadyExistsException(user.getUserName());
        }

        UserProfile userProfile = new UserProfile();
        UserPermissions userPermissions = new UserPermissions();
        userProfile.setUserEmail(user.getUserEmail());
        userPermissions.setUserEmail(user.getUserEmail());
        user.setUserId(UUID.randomUUID().toString());
        user.setUserProfile(userProfile);
        user.setUserPermissions(userPermissions);
        user.setUserProfilePhoto(env.getProperty("user.default.profile-photo"));

        return userRepository.save(user);


    }

    @Transactional
    @Override
    @CacheEvict(value = "User", key = "#email")
    public boolean deleteUser(@ExistsEmail String email) {
        userRepository.deleteById(email);
        return true;
    }

    @Transactional
    @Override
    @Caching(evict = {@CacheEvict(value = "User", key = "#user.userEmail"), @CacheEvict(value = "User", key = "#user.userName")})
    public User updateUser(@ExistsEmail User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    @CacheEvict(value = "User", key = "#username")
    public boolean updateUserPermissions(@ExistsUsername String username, UserPermissions userPermissions) {
        User user = userRepository.findByUserName(username);
        userPermissions.setUserEmail(user.getUserEmail());
        userRepository.updateUserPermissions(userPermissions);
        return true;
    }

    @Transactional
    @Override
    @CacheEvict(value = "User", key = "#request.get('email')")
    public void updatePassword(HashMap<String, String> request) {
        String email = request.get("email");
        String userId = request.get("userid");
        String ipAddress = request.get("ipaddress");
        String userAgent = request.get("useragent");
        String password = request.get("password");
        if (userRepository.existsByUserEmailandUserId(email, userId) == 1) {
            userRepository.updateUserPassword(email, password);
            try {
                emailService.sendSuccessResetPassword(email, ipAddress, userAgent);

            } catch (MessagingException e) {
                throw new MailException(email);
            }
        } else {
            throw new UserEmailNotFoundException(email);
        }

    }

    @Transactional
    @Override
    @Caching(evict = {@CacheEvict(value = "User", key = "#email"), @CacheEvict(value = "User", key = "#username")})
    public void updateUserUsername(@ExistsEmail String email, @ExistsUsername String username) {
        userRepository.updateUserUsernameOnUser(email, username);
        userRepository.updateUserUsernameOnComment(username);
    }

    @Transactional
    @Override
    @CacheEvict(value = "User", allEntries = true)
    public void updateUserGithubAddress(@ExistsEmail String email, String github) {
        userRepository.updateGithubAddress(email, github);
    }

    @Transactional
    @Override
    @CacheEvict(value = "User",allEntries = true)
    public void updateUserLinkedinAddress(@ExistsEmail String email, String linkedin) {
        userRepository.updateLinkedinAddress(email, linkedin);
    }

    @Transactional
    @Override
    @CacheEvict(value = "User", allEntries = true)
    public void updateUserBirthDate(@ExistsEmail String email, String date) {
        userRepository.updateUserBirthDate(email, date);
    }

    @Transactional
    @Override
    @CacheEvict(value = "User", allEntries = true)
    public void updateUserImage(MultipartFile file,@ExistsId String userId) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
            Path path = Paths.get("/home/kaya/Desktop/Angular-Projects/kayafirat.com-front-end/src/assets/upload/" + userId + "." + file.getOriginalFilename().split("\\.")[1]);
            Files.write(path, bytes);

            userRepository.updateUserPhoto(userId, path.toString().substring(64));
            commentRepository.updateUserPhoto(userRepository.findByUserId(userId).getUserName(), path.toString().substring(64));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean updateUserVerification(@ExistsId String userId, @ExistsEmail String email) {
        return true;
    }

    public String authenticateUser(AuthenticationRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password. ", e);
        }
        final UserDetails userDetails = loadUserByUsername(authRequest.getUsername());
        return jwtUtil.generateToken(userDetails);


    }

    @Override
    @Cacheable(cacheNames = "UserToken", key = "'loadByUsername'+ #email")
    public UserDetails loadUserByUsername(@ExistsEmail String email) throws UsernameNotFoundException {
        List<Object[]> user = userRepository.findUser(email);
        String userEmail = user.get(0)[0].toString();
        String userPass = bCryptPasswordEncoder.encode(user.get(0)[1].toString());
        return new org.springframework.security.core.userdetails.User(userEmail, userPass, new ArrayList<>());


    }

    @Override
    public Collection<?> searchUser(String keyword) {
        return userRepository.searchByUsernameAndUseremail(keyword, UserExceptr.class);
    }

    @Override
    public String validateCaptcha(String key) {
        String url = env.getProperty("google.recaptcha.verify-link") + "secret=" + env.getProperty("google.recaptcha.secret-key") + "&response=" + key;
        return restTemplate.getForObject(url, String.class);
    }
}
