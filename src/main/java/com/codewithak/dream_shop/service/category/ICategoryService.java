package com.codewithak.dream_shop.service.category;

import com.codewithak.dream_shop.dto.CategoryDto;
import com.codewithak.dream_shop.model.Category;

import java.util.List;

public interface ICategoryService {

    CategoryDto getCategoryById(Long id);

    CategoryDto getCategoryByName(String name);

    List<CategoryDto> getAllCategories();

    CategoryDto addCategory(Category category);

    CategoryDto updateCategory(Category category, Long id);

    void deleteCategoryById(Long id);


    List<CategoryDto> getConvertedCategories(List<Category> categories);

    CategoryDto convertToDTO(Category category);
}
