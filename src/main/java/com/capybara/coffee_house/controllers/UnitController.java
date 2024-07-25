package com.capybara.coffee_house.controllers;

import com.capybara.coffee_house.dto.CategoryDto;
import com.capybara.coffee_house.dto.UnitDto;
import com.capybara.coffee_house.entities.Category;
import com.capybara.coffee_house.entities.Unit;
import com.capybara.coffee_house.services.UnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/unit")
public class UnitController {

    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping({"", "/"})
    public String showUnitList(Model model) {
        model.addAttribute("units", unitService.findAll());
        return "unit/list_unit";
    }

    @GetMapping("/new")
    public String showCreatePageUnit(Model model) {
        UnitDto unitDto = new UnitDto();
        model.addAttribute("unitDto", unitDto);
        return "unit/form_unit";
    }

    @GetMapping("/edit/{id}")
    public String showEditUnit(@PathVariable Integer id, Model model) {
        Unit unit = unitService.findById(id);
        UnitDto unitDto = unitService.convertToDto(unit);
        model.addAttribute("unitDto", unitDto);
        return "unit/form_unit";
    }

    @PostMapping("/save")
    public String saveUnit(@Valid @ModelAttribute UnitDto unitDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "unit/form_unit";
        }
        Unit unit = unitService.convertFromDto(unitDto);
        unitService.save(unit);
        return "redirect:/unit";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        unitService.delete(id);
        return "redirect:/unit";
    }
}
