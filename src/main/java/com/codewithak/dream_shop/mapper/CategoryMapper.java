package com.codewithak.dream_shop.mapper;

import com.codewithak.dream_shop.dto.CategoryDto;
import com.codewithak.dream_shop.model.Category;

public class CategoryMapper {
    public static CategoryDto toDto (Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}
