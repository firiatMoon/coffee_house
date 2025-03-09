package com.capybara.services;

import com.capybara.dto.CategoryDto;
import com.capybara.entities.Category;
import com.capybara.exceptions.EntityNotFoundException;
import com.capybara.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public Category findByTitle(String title) {
        return categoryRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDto convertToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .build();
    }

    public Category convertFromDto(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .title(categoryDto.getTitle())
                .build();
    }
}
