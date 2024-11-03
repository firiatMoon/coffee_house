package com.capybara.coffee_house.controllers;

import com.capybara.coffee_house.dto.ProductDto;
import com.capybara.coffee_house.services.CategoryService;
import com.capybara.coffee_house.entities.Product;
import com.capybara.coffee_house.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @GetMapping({"", "/"})
    public String getAllPages(@RequestParam(value = "categoryId", required = false) String categoryId,
                              @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
                              @RequestParam(defaultValue = "") String keyword,
                              Model model) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = null;
        List<Product> products = null;
        if(keyword.isEmpty()) {
            page = categoryId == null ? productService.findPage(pageable)
                    : productService.findPageByCategory(Integer.parseInt(categoryId), pageable);
        } else{
            page = productService.findByKeyword(keyword, pageable);
        }

        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        products = page.getContent();

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("keyword", keyword);
        return "product/list";
    }

    @GetMapping("/new")
    public String showCreatePageProduct(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        ProductDto productDto = productService.convertToDto(product);
        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "product/form";
        }
        Product product = productService.convertFromDto(productDto);
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/product";
    }
}
