package com.capybara.coffee_house.controllers;

import com.capybara.coffee_house.dto.ProductDto;
import com.capybara.coffee_house.services.CategoryService;
import com.capybara.coffee_house.entities.Product;
import com.capybara.coffee_house.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

//    @GetMapping({"", "/"})
//    public String showProductList(@RequestParam(value = "categoryId", required = false) String categoryId,
//                                  Model model, String keyword) {
//        List<Product> products = categoryId == null ? productService.findAll()
//                : productService.findAllByCategory(Integer.parseInt(categoryId));
//        model.addAttribute("products", products);
//        model.addAttribute("categories", categoryService.findAll());
//        return "product/list";
//    }

    @GetMapping({"", "/"})
    public String getAllPages(Model model, @RequestParam(value = "categoryId", required = false) String categoryId) {
        return getOnePage(model, categoryId, 1);
    }

    @GetMapping("/{pageNumber}")
    public String getOnePage(Model model, @RequestParam(value = "categoryId", required = false) String categoryId,
                             @PathVariable int pageNumber) {

        Page<Product> page = categoryId == null ? productService.findPage(pageNumber)
                : productService.findPageByCategory(Integer.parseInt(categoryId), pageNumber);
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
       // Category category = categoryService.findById(Integer.parseInt(categoryId));
        List<Product> products = page.getContent();

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        //model.addAttribute("category", categoryId);
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
