package com.capybara.coffee_house.repositories;

import com.capybara.coffee_house.entities.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByIdIn(List<Long> ids);

    Page<Menu> findByProductId(Long product_id, Pageable pageable);

}
