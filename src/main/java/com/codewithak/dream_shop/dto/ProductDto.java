package com.codewithak.dream_shop.dto;

import com.codewithak.dream_shop.model.Category;
import com.codewithak.dream_shop.model.Product;
import lombok.*;

import java.math.BigDecimal;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private CategoryDto category;

}
