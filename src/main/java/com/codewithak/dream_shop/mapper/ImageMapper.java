package com.codewithak.dream_shop.mapper;

import com.codewithak.dream_shop.dto.ImageDto;
import com.codewithak.dream_shop.model.Image;

public class ImageMapper {
    public static ImageDto toDto(Image image) {
        return new ImageDto(
                image.getId(),
                image.getFileName(),
                image.getDownloadUrl()
        );
    }
}
