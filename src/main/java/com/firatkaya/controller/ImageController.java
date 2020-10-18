package com.firatkaya.controller;

import com.firatkaya.entity.Image;
import com.firatkaya.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/v1/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping(value = "/detail")
    public ResponseEntity<?> getImage(@RequestParam String id){
        return ResponseEntity.ok(imageService.getImage(id));
    }

    @PostMapping
    public ResponseEntity<?> saveImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.saveImage(file));
    }
    @DeleteMapping
    public ResponseEntity<?> deleteImage(@RequestParam String id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
