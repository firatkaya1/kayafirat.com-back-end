package com.firatkaya.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.firatkaya.entity.Comment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping()
public class TestController {

    private final RestTemplate restTemplate;

    public TestController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping(value = "/test")
    public ResponseEntity<?> getSingleComments(){


        return ResponseEntity.ok("myMap");
    }
    @PostMapping(value = "/test")
    public ResponseEntity<String> getSingleComment(@RequestParam("code") String code){
        System.out.println("access token :"+code);

        return ResponseEntity.ok(code);
    }

}
