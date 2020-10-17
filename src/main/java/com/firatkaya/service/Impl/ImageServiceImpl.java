package com.firatkaya.service.Impl;

import com.firatkaya.entity.Image;
import com.firatkaya.repository.ImageRepository;
import com.firatkaya.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageServiceImpl implements ImageService {


    ImageRepository imageRepository;

    @Override
    public Optional<Image> getImage(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image saveImage(Image image, MultipartFile file) {
        return imageRepository.save(image);
    }

    @Override
    public void updateImage(Image _image, Long id) {
        _image.setId(id);
        imageRepository.save(_image);
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
