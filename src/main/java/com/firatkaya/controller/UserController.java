package com.firatkaya.controller;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.firatkaya.entity.User;
import com.firatkaya.entity.UserPermissions;
import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.EmailService;
import com.firatkaya.service.UserService;

/**
 * @author firatkaya
 * @version 1.0.0
 */

@RestController
@RequestMapping("api/v1/user")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable(value = "username")  String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping(value = "/photo/{email}")
    public ResponseEntity<?> getUserPhotoByEmail(@PathVariable(value = "email") String email) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/username/photo/{username}")
    public ResponseEntity<?> getUserPhotoByUsername(@PathVariable(value = "username")  String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username).getUserProfilePhoto());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> addUser(@Validated @RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = "/permissions/{username}")
    public ResponseEntity<?> updateUserPermissions(@RequestBody UserPermissions userPermissions, @PathVariable(value = "username")  String username) {
        return ResponseEntity.ok(userService.updateUserPermissions(username, userPermissions));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUserUsername(@RequestBody HashMap<String, String> request) {
        String key = request.get("key");
        String usermail = request.get("email");
        switch (key) {
            case "username":
                userService.updateUserUsername(usermail, request.get("username"));
                break;
            case "githubaddress":
                userService.updateUserGithubAddress(usermail, request.get("githubaddress"));
                break;
            case "linkedinaddress":
                userService.updateUserLinkedinAddress(usermail, request.get("linkedinaddress"));
                break;
            case "birthdate":
                userService.updateUserBirthDate(usermail, request.get("birthdate"));
                break;
            default:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();


    }

    @PostMapping(value = "/photo/{userId}")
    public ResponseEntity<?> updatePicture(@RequestParam("file") MultipartFile file, @PathVariable(value = "userId") String userId) {
        userService.updateUserImage(file, userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "search/{keyword}")
    public ResponseEntity<Collection<?>> searchUser(@PathVariable(value = "keyword") String keyword) {
        return ResponseEntity.ok(userService.searchUser(keyword));
    }


}
