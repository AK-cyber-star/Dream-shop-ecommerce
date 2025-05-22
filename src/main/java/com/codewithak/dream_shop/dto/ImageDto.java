package com.codewithak.dream_shop.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageDto {

    private Long imageId;
    private String imageName;
    private String downloadUrl;

}
