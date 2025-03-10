package com.capybara.controllers;

import com.capybara.dto.CategoryDto;
import com.capybara.entities.Category;
import com.capybara.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public String showCategoryList(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list_category";
    }

    @GetMapping("/new")
    public String showCreatePageProduct(Model model) {
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("categoryDto", categoryDto);
        return "category/form_category";
    }

    @GetMapping("/edit/{id}")
    public String showEditProduct(@PathVariable Integer id, Model model) {
        Category category = categoryService.findById(id);
        CategoryDto categoryDto = categoryService.convertToDto(category);
        model.addAttribute("categoryDto", categoryDto);
        return "category/form_category";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute CategoryDto categoryDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "category/form_category";
        }
        Category category = categoryService.convertFromDto(categoryDto);
        categoryService.save(category);
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        categoryService.delete(id);
        return "redirect:/category";
    }
}
