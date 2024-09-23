package com.capybara.coffee_house.controllers.api;

import com.capybara.coffee_house.dto.ClientBonusCardDto;
import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.services.BonusCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientBonusCard")
public class BonusCardController {

    private final BonusCardService bonusCardService;

    @Autowired
    public BonusCardController(BonusCardService bonusCardService) {
        this.bonusCardService = bonusCardService;
    }

    @GetMapping
    public ClientBonusCardDto getBonusCard(@RequestParam @Valid String phone) {
        BonusCard bonusCard = bonusCardService.getByPhoneNumber(phone);
        return bonusCardService.convertToDto(bonusCard);
    }
}
