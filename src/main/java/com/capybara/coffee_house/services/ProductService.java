package com.capybara.coffee_house.services;

import com.capybara.coffee_house.dto.ProductDto;
import com.capybara.coffee_house.entities.Product;
import com.capybara.coffee_house.exceptions.EntityNotFoundException;
import com.capybara.coffee_house.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findPage(Pageable pageable) {
        return productRepository.findAll(pageable);
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
        ProductDto.ProductDtoBuilder builderDto = ProductDto.builder();
        return builderDto
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .build();
    }

    //Конвертацию из ProductDto в Product
    public Product convertFromDto(ProductDto productDto) {
        Product.ProductBuilder builder = Product.builder();
        log.info("{}",productDto.getCategory());
        return builder
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .category(productDto.getCategory())
                .build();
    }
}
