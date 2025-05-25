package com.codewithak.dream_shop.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageDto {

    private Long id;
    private String fileName;
    private String downloadUrl;

}
