package com.copybara.coffee_house.services;

import com.copybara.coffee_house.dto.ProductDto;
import com.copybara.coffee_house.entities.Product;
import com.copybara.coffee_house.exceptions.EntityNotFoundException;
import com.copybara.coffee_house.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
                .price(product.getPrice())
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
                .price(productDto.getPrice())
                .build();
    }
}
