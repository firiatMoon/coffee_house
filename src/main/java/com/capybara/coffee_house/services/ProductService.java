package com.capybara.coffee_house.services;

import com.capybara.coffee_house.dto.CategoryDto;
import com.capybara.coffee_house.dto.ProductDto;
import com.capybara.coffee_house.entities.Category;
import com.capybara.coffee_house.entities.Product;
import com.capybara.coffee_house.exceptions.EntityNotFoundException;
import com.capybara.coffee_house.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    //get products by keyword
    public Page<Product> findByKeyword(String keyword, Pageable pageable) {
        return productRepository.findByKeywordIgnoreCase(keyword, pageable);
    }

    public Page<Product> findPageByCategory(Integer categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryId(categoryId, pageable);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Product with id = %s not found", id)
                ));
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    //Конвертацию из Product в ProductDto
    public ProductDto convertToDto(Product product) {
        CategoryDto categoryDto = Optional.ofNullable(product.getCategory())
                .map(categoryService::convertToDto)
                .orElse(new CategoryDto(null, null));
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(categoryDto)
                .build();
    }

    //Конвертацию из ProductDto в Product
    public Product convertFromDto(ProductDto productDto) {
        Category category = Optional.ofNullable(productDto.getCategory())
                        .map(categoryService::convertFromDto)
                                .orElse(new Category(null, null));
        log.info("{}",productDto.getCategory());
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .category(category)
                .build();
    }
}
