package com.capybara.coffee_house.controllers;

import com.capybara.coffee_house.dto.MenuDto;
import com.capybara.coffee_house.entities.Menu;
import com.capybara.coffee_house.services.MenuService;
import com.capybara.coffee_house.services.ProductService;
import com.capybara.coffee_house.services.UnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    private final ProductService productService;
    private final UnitService unitService;

    @Autowired
    public MenuController(MenuService menuService, ProductService productService, UnitService unitService) {
        this.menuService = menuService;
        this.productService = productService;
        this.unitService = unitService;
    }

    @GetMapping({"", "/"})
    public String getAllPages(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
                              Model model){

        Page<Menu> page = menuService.findPage(PageRequest.of(pageNumber, pageSize));
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        List<Menu> menus = page.getContent();

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("menu", menus);
        return "menu/list_menu";
    }

    @GetMapping("/new")
    public String showCreatePageMenu(Model model) {
        MenuDto menuDto = new MenuDto();
        model.addAttribute("menuDto", menuDto);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("units", unitService.findAll());
        return "menu/form_menu";
    }

    @GetMapping("/edit/{id}")
    public String showEditMenu(@PathVariable Long id, Model model) {
        Menu menu = menuService.getMenuById(id);
        MenuDto menuDto = menuService.convertToDto(menu);
        model.addAttribute("menuDto", menuDto);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("unit", unitService.findAll());
        return "menu/form_menu";
    }

    @PostMapping("/save")
    public String saveMenu(@Valid @ModelAttribute MenuDto menuDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "menu/form_menu";
        }
        Menu menu = menuService.convertFromDto(menuDto);
        menuService.save(menu);
        return "redirect:/menu";
    }

    @GetMapping("/delete/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return "redirect:/menu";
    }
}
