package com.firatkaya.controller;

import java.util.Collection;
import java.util.HashMap;


import com.firatkaya.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.firatkaya.service.UserService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author firatkaya
 * @version 1.0.0
 */

@RestController
@RequestMapping("/v1/user")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserByUsername() {
        return ResponseEntity.ok(userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @GetMapping(value = "/photo")
    public ResponseEntity<?> getPhotoByEmail() {
        return ResponseEntity.ok(userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getUserProfilePhoto());
    }

    @GetMapping(value = "/username/photo/{username}")
    public ResponseEntity<?> getPhotoByUsername(@PathVariable(value = "username")  String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username).getUserProfilePhoto());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> addUser(@Validated @RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = "/permissions")
    public ResponseEntity<?> updatePermissions(@RequestBody UserPermissions userPermissions) {
        userService.updateUserPermissions(userPermissions);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateSubInfos(@RequestBody HashMap<String, String> request) {
        String key = request.get("key");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        switch (key) {
            case "username":
                userService.updateUserUsername(email, request.get("username"));
                break;
            case "github":
                userService.updateUserGithubAddress(email, request.get("githubaddress"));
                break;
            case "linkedin":
                userService.updateUserLinkedinAddress(email, request.get("linkedinaddress"));
                break;
            case "birthdate":
                userService.updateUserBirthDate(email, request.get("birthdate"));
                break;
            default:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();


    }

    @PostMapping(value = "/photo")
    public ResponseEntity<?> updatePicture(@RequestParam("file") MultipartFile file){
        userService.updateUserImage(file, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "search/{keyword}")
    public ResponseEntity<Collection<?>> search(@PathVariable(value = "keyword") String keyword) {
        return ResponseEntity.ok(userService.searchUser(keyword));
    }


}
