package com.capybara.repositories;

import com.capybara.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategoryId(Integer categoryId, Pageable pageable);

    Product findByName(String name);

    @Query(value = "select * from product p where p.name like %:keyword%", nativeQuery = true)
    Page<Product> findByKeywordIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
}
