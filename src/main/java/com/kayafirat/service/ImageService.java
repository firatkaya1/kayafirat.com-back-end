package com.kayafirat.service;


import com.kayafirat.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Image getImage(String id);

    List<Image> getAllImages();

    Image saveImage(MultipartFile file);

    void updateImage(Image image,String id);

    void deleteImage(String id);



}
