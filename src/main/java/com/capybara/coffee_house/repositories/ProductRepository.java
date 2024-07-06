package com.capybara.coffee_house.repositories;

import com.capybara.coffee_house.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select p from Product p where p.name LIKE %?1%")
    List<Product> findByKeyword(String keyword);

    Page<Product> findAllByCategoryId(Integer categoryId, Pageable pageable);
}
