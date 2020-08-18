package com.firatkaya.controller;

import org.springframework.http.ResponseEntity;

import com.firatkaya.entity.Comment;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping()
public class TestController {



    @GetMapping(value = "/test")
    public ResponseEntity<String> getSingleCommesnts(@RequestParam("code") String code){
        System.out.println("access token :"+code);

        return ResponseEntity.ok(code);
    }
    @PostMapping(value = "/test")
    public ResponseEntity<String> getSingleComments(@RequestParam("code") String code){
        System.out.println("access token :"+code);

        return ResponseEntity.ok(code);
    }

}
