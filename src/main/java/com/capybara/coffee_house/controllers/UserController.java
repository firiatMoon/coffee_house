package com.capybara.coffee_house.controllers;

import com.capybara.coffee_house.dto.UserDto;
import com.capybara.coffee_house.dto.UserFormDto;
import com.capybara.coffee_house.entities.User;
import com.capybara.coffee_house.services.UserFormService;
import com.capybara.coffee_house.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserFormService userFormService;

    @Autowired
    public UserController(UserService userService, UserFormService userFormService) {
        this.userService = userService;
        this.userFormService = userFormService;
    }

    @GetMapping({"", "/"})
    public String showUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/new")
    public String showCreatePageUser(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "user/user_form";
    }

    @PostMapping("/save")
    public String createUser(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/user_form";
        }
        userService.save(userDto);
        return "redirect:/user";
    }

    @GetMapping("/edit/{id}")
    public String showEditUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        UserFormDto userFormDto = userFormService.convertToDto(user);
        model.addAttribute("userDto", userFormDto);
        return "user/user_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/user";
    }
}
