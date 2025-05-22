package com.codewithak.dream_shop.mapper;

import com.codewithak.dream_shop.dto.CategoryDto;
import com.codewithak.dream_shop.dto.ProductDto;
import com.codewithak.dream_shop.model.Product;

public class ProductMapper {
    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                new CategoryDto(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                )
        );
    }
}
