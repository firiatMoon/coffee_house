package com.copybara.coffee_house.repositories;

import com.copybara.coffee_house.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
