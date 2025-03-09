package com.capybara.services;

import com.capybara.dto.MenuDto;
import com.capybara.dto.ProductDto;
import com.capybara.dto.UnitDto;
import com.capybara.entities.Menu;
import com.capybara.entities.Product;
import com.capybara.entities.Unit;
import com.capybara.exceptions.EntityNotFoundException;
import com.capybara.repositories.MenuRepository;
import com.capybara.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ProductService productService;
    private final UnitService unitService;
    private final ProductRepository productRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, ProductService productService, UnitService unitService, ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productService = productService;
        this.unitService = unitService;
        this.productRepository = productRepository;
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    //get products by keyword
    public Page<Menu> findByKeyword(String keyword, Pageable pageable) {
        Long productId = productRepository.findByName(keyword).getId();
        return menuRepository.findByProductId(productId, pageable);
    }

    public Page<Menu> findPage(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    public Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() -> new EntityNotFoundException("Menu not found"));
    }

    public void save(Menu menu) {
        menuRepository.save(menu);
    }

    public void delete(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    public List<Menu> findMenuByIdIn(List<Long> menuIds) {
        return menuRepository.findByIdIn(menuIds);
    }

    public MenuDto convertToDto(Menu menu) {
        ProductDto productDto = Optional.ofNullable(menu.getProduct())
                .map(productService::convertToDto)
                .orElse(new ProductDto(null, null, null, null));

        UnitDto unitDto = Optional.ofNullable(menu.getUnit()).
                map(unitService::convertToDto)
                .orElse(new UnitDto(null, null));
        return MenuDto.builder()
                .id(menu.getId())
                .product(productDto)
                .unit(unitDto)
                .quantity(menu.getQuantity())
                .price(menu.getPrice())
                .build();
    }

    public Menu convertFromDto(MenuDto menuDto) {
        Product product = Optional.ofNullable(menuDto.getProduct())
                .map(productService::convertFromDto)
                .orElse(new Product(null, null, null, null));

        Unit unit = Optional.ofNullable(menuDto.getUnit())
                .map(unitService::convertFromDto)
                .orElse(new Unit(null, null));
        return Menu.builder()
                .id(menuDto.getId())
                .product(product)
                .unit(unit)
                .quantity(menuDto.getQuantity())
                .price(menuDto.getPrice())
                .build();
    }
}
