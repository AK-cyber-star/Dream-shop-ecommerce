package com.codewithak.dream_shop.service.category;

import com.codewithak.dream_shop.dto.CategoryDto;
import com.codewithak.dream_shop.exceptions.AlreadyExistsException;
import com.codewithak.dream_shop.exceptions.ResourceNotFoundException;
import com.codewithak.dream_shop.mapper.CategoryMapper;
import com.codewithak.dream_shop.model.Category;
import com.codewithak.dream_shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        return CategoryMapper.toDto(category);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        return CategoryMapper.toDto(categoryRepository.findByName(name));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryDto addCategory(Category category) {
        Category addedCategory = Optional.ofNullable(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));

        return CategoryMapper.toDto(addedCategory);
    }

    @Override
    public CategoryDto updateCategory(Category category, Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        existingCategory.setName(category.getName());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found!");
                });
    }
}
