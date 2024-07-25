package com.capybara.coffee_house.services;

import com.capybara.coffee_house.dto.MenuDto;
import com.capybara.coffee_house.entities.Menu;
import com.capybara.coffee_house.exceptions.EntityNotFoundException;
import com.capybara.coffee_house.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
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

    public MenuDto convertToDto(Menu menu) {
        MenuDto.MenuDtoBuilder builderDto = MenuDto.builder();
        return builderDto
                .id(menu.getId())
                .product(menu.getProduct())
                .unit(menu.getUnit())
                .quantity(menu.getQuantity())
                .price(menu.getPrice())
                .build();
    }

    public Menu convertFromDto(MenuDto menuDto) {
        Menu.MenuBuilder builder = Menu.builder();
        return builder
                .id(menuDto.getId())
                .product(menuDto.getProduct())
                .unit(menuDto.getUnit())
                .quantity(menuDto.getQuantity())
                .price(menuDto.getPrice())
                .build();
    }
}
