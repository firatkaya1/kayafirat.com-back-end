package com.kayafirat.service.Impl;

import com.kayafirat.entity.Image;
import com.kayafirat.repository.ImageRepository;
import com.kayafirat.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    Environment environment;

    @Override
    public Image getImage(String id) {
        return imageRepository.getOne(id);
    }

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Image saveImage( MultipartFile file) {
        Image image = new Image();
        byte[] bytes;
        try {
            String id = UUID.randomUUID().toString();
            String pathURI = environment.getProperty("user.default.blog-photo") + id+ "." + file.getOriginalFilename().split("\\.")[1];
            bytes = file.getBytes();
            Path path = Paths.get(pathURI);
            Files.write(path, bytes);
            image.setId(id);
            image.setImageName(id);
            image.setImageURL(pathURI);
            image.setImageCreatedDate(new Date().toString());
            imageRepository.save(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageRepository.save(image);
    }

    @Override
    public void updateImage(Image _image, String id) {
        _image.setId(id);
        imageRepository.save(_image);
    }

    @Override
    public void deleteImage(String id) {
        imageRepository.deleteById(id);
    }
}
