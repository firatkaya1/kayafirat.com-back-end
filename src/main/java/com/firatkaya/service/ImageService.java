package com.firatkaya.service;


import com.firatkaya.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {

    Optional<Image> getImage(Long id);

    Image saveImage(Image image, MultipartFile file);

    void updateImage(Image image,Long id);

    void deleteImage(Long id);



}
