package com.codewithak.dream_shop.service.category;

import com.codewithak.dream_shop.dto.CategoryDto;
import com.codewithak.dream_shop.exceptions.AlreadyExistsException;
import com.codewithak.dream_shop.exceptions.ResourceNotFoundException;
import com.codewithak.dream_shop.model.Category;
import com.codewithak.dream_shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        return convertToDTO(category);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        return convertToDTO(categoryRepository.findByName(name));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return getConvertedCategories(categories);
    }


    @Override
    public CategoryDto addCategory(Category category) {
        Category addedCategory = Optional.ofNullable(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));

        return convertToDTO(addedCategory);
    }

    @Override
    public CategoryDto updateCategory(Category category, Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        existingCategory.setName(category.getName());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDTO(updatedCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found!");
                });
    }

    @Override
    public List<CategoryDto> getConvertedCategories(List<Category> categories) {
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto convertToDTO(Category category) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
}
