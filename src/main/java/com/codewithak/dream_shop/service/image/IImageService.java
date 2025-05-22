package com.codewithak.dream_shop.service.image;

import com.codewithak.dream_shop.dto.ImageDto;
import com.codewithak.dream_shop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile file, Long imageId);

}
