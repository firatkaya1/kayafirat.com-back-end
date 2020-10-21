package com.kayafirat.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.kayafirat.model.AuthenticationRequest;
import com.kayafirat.model.projection.UserDetailExcept;
import com.kayafirat.util.JwtUtil;
import com.kayafirat.util.SecurityUtil;
import com.kayafirat.validation.constraint.ExistsEmail;
import com.kayafirat.validation.constraint.ExistsId;
import com.kayafirat.validation.constraint.ExistsUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.kayafirat.entity.User;
import com.kayafirat.entity.UserPermissions;
import com.kayafirat.entity.UserProfile;
import com.kayafirat.exceptions.customExceptions.MailException;
import com.kayafirat.exceptions.customExceptions.UserEmailAlreadyExistsException;
import com.kayafirat.exceptions.customExceptions.UserNameAlreadyExistsException;
import com.kayafirat.exceptions.customExceptions.UserEmailNotFoundException;
import com.kayafirat.model.projection.UserExceptr;
import com.kayafirat.repository.CommentRepository;
import com.kayafirat.repository.UserRepository;
import com.kayafirat.service.EmailService;
import com.kayafirat.service.UserService;


@Service
public class IUserService implements UserService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final EmailService emailService;

    @Autowired
    private Environment env;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    SecurityUtil securityUtil;

    private final RestTemplate restTemplate;

    @Autowired
    public IUserService(RestTemplateBuilder restTemplateBuilder,
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
    public Collection<UserDetailExcept> getAll() {
        return userRepository.findAll(UserDetailExcept.class);
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
        user.setRole("ROLE_USER");
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
    @CacheEvict(value = "User", allEntries = true)
    public boolean updateUserPermissions(UserPermissions userPermissions) {
        userRepository.updateUserPermissions(userPermissions);
        return true;
    }

    @Transactional
    @Override
    @CacheEvict(value = "User", key = "#request.get('email')")
    public void updatePassword(HashMap<String, String> request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String userId = request.get("userid");
        String ipAddress = request.get("ipaddress");
        String userAgent = request.get("useragent");
        String password = request.get("password");
        if (userRepository.existsByUserEmailUserId(email, userId) == 1) {
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
    public void updateUserImage(MultipartFile file,@ExistsEmail String userEmail) {
        byte[] bytes;
        User user = userRepository.findByUserEmail(userEmail);
        try {
            String pathURI = env.getProperty("user.default.profile-photo") +  user.getUserId()+ "." + file.getOriginalFilename().split("\\.")[1];
            bytes = file.getBytes();
            Path path = Paths.get(pathURI);
            Files.write(path, bytes);

            userRepository.updateUserPhoto(user.getUserEmail(),path.toString());
            commentRepository.updateUserPhoto(user.getUserName(), path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean updateUserVerification(@ExistsId String userId, @ExistsEmail String email) {
            User user = getUserByEmail(email);
            user.setVerification(true);
            updateUser(user);
            try {
                emailService.sendSuccessVerification(user.getUserEmail());
            } catch (MessagingException e) {
                throw new MailException(email);
            }
            return true;

    }

    public String authenticateUser(AuthenticationRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password. ", e);
        }
        final UserDetails userDetails = loadUserByUsername(authRequest.getUsername());
        return jwtUtil.generateToken(userDetails);


    }

    @Override
    @Cacheable(cacheNames = "UserToken", key = "'loadByUsername'+ #email")
    public UserDetails loadUserByUsername(@ExistsEmail String email) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(email);
        final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUserEmail(), securityUtil.encode(user.getUserPassword()),new ArrayList<>() );


    }

    @Override
    public Collection<?> searchUser(String keyword) {
        return userRepository.searchByUsernameAndUserEmail(keyword, UserExceptr.class);
    }

    // bu method silinecektir.
    //GÜVENLİK ZAFİYETİ
    @Override
    public String validateCaptcha(String key) {
        String url = env.getProperty("google.recaptcha.verify-link") + "secret=" + env.getProperty("google.recaptcha.secret-key") + "&response=" + key;
        String test =restTemplate.getForObject(url, String.class);
        System.out.println("test : "+test);
        test = test.substring(test.indexOf("\":")+3);
        System.out.println("test sprt : "+test.substring(0,test.indexOf(",")));
        return restTemplate.getForObject(url, String.class);
    }
}
