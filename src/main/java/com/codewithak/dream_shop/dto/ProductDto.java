package com.codewithak.dream_shop.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private CategoryDto category;
    private List<ImageDto> images;

}
