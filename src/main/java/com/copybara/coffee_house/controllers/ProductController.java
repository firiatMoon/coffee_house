package com.copybara.coffee_house.controllers;

import com.copybara.coffee_house.dto.ProductDto;
import com.copybara.coffee_house.entities.Product;
import com.copybara.coffee_house.exceptions.EntityNotFoundException;
import com.copybara.coffee_house.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"", "/"})
    public String showProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }

    @GetMapping("/new")
    public String showCreatePage(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "product/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        ProductDto productDto = productService.convertToDto(product);
        model.addAttribute("productDto", productDto);
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
