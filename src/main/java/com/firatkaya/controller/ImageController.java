package com.firatkaya.controller;

import com.firatkaya.entity.Image;
import com.firatkaya.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/v1/image")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

    ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getImage(@RequestParam("id") String _id){
        System.out.println("bana gelen id :"+_id);
        Long id = Long.getLong(_id);
       Optional<Image> image =  imageService.getImage(id);
        return ResponseEntity.ok(image);
    }

    @PostMapping
    public ResponseEntity<?> saveImage(@RequestParam("file") MultipartFile file, @RequestBody Image image) {
        return ResponseEntity.ok(imageService.saveImage(image,file));
    }
    @PutMapping
    public ResponseEntity<?> updateImage(@RequestParam Long id,@RequestBody Image image) {
        imageService.updateImage(image,id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteImage(@RequestParam Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
