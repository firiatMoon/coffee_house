package com.copybara.coffee_house.services;

import com.copybara.coffee_house.entities.Category;
import com.copybara.coffee_house.exceptions.EntityNotFoundException;
import com.copybara.coffee_house.repositories.CategoryRepository;
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
//    public Category findById(Integer id) {
//        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
//    }
}
