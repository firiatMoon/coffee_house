package com.capybara.coffee_house.services;

import com.capybara.coffee_house.dto.MenuDto;
import com.capybara.coffee_house.dto.ProductDto;
import com.capybara.coffee_house.dto.UnitDto;
import com.capybara.coffee_house.entities.Menu;
import com.capybara.coffee_house.entities.Product;
import com.capybara.coffee_house.entities.Unit;
import com.capybara.coffee_house.exceptions.EntityNotFoundException;
import com.capybara.coffee_house.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ProductService productService;
    private final UnitService unitService;

    @Autowired
    public MenuService(MenuRepository menuRepository, ProductService productService, UnitService unitService) {
        this.menuRepository = menuRepository;
        this.productService = productService;
        this.unitService = unitService;
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Page<Menu> findPage(PageRequest pageRequest) {
        return menuRepository.findAll(pageRequest);
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
        MenuDto.MenuDtoBuilder builderDto = MenuDto.builder();
        ProductDto productDto = Optional.ofNullable(menu.getProduct())
                .map(productService::convertToDto)
                .orElse(new ProductDto(null, null, null, null));

        UnitDto unitDto = Optional.ofNullable(menu.getUnit()).
                map(unitService::convertToDto)
                .orElse(new UnitDto(null, null));
        return builderDto
                .id(menu.getId())
                .product(productDto)
                .unit(unitDto)
                .quantity(menu.getQuantity())
                .price(menu.getPrice())
                .build();
    }

    public Menu convertFromDto(MenuDto menuDto) {
        Menu.MenuBuilder builder = Menu.builder();
        Product product = Optional.ofNullable(menuDto.getProduct())
                .map(productService::convertFromDto)
                .orElse(new Product(null, null, null, null));

        Unit unit = Optional.ofNullable(menuDto.getUnit())
                .map(unitService::convertFromDto)
                .orElse(new Unit(null, null));
        return builder
                .id(menuDto.getId())
                .product(product)
                .unit(unit)
                .quantity(menuDto.getQuantity())
                .price(menuDto.getPrice())
                .build();
    }
}
