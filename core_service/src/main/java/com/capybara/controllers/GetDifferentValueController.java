package com.capybara.controllers;

import com.capybara.services.BonusCardService;
import com.capybara.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pass")
public class GetDifferentValueController {

    private final ClientService clientService;
    private final BonusCardService bonusCardService;

    @Autowired
    public GetDifferentValueController(ClientService clientService, BonusCardService bonusCardService) {
        this.clientService = clientService;
        this.bonusCardService = bonusCardService;
    }

    @PostMapping("/setChatId")
    public void setChatId(@RequestBody Map<String, Long> map) {
        Long chatId = map.get("chatId");
        clientService.createClient(chatId);
    }

    @PostMapping("/setUsername")
    public void setUsername(@RequestBody Map<String, String> map){
        clientService.saveUsername(map);
    }

    @PostMapping("/getBonusCard")
    public String getBonusCard(@RequestBody String chatId){
        log.info("Received in core_service - {}", chatId);
        return bonusCardService.getByChatId(chatId);
    }

    @PostMapping("/getIsActive")
    public boolean getIsActive(@RequestBody Long chatId){
        return clientService.getIsActive(chatId);
    }

    @PostMapping("/setBirthday")
    public void setBirthday(@RequestBody Map<String, String> map){
        clientService.saveBirthday(map);
    }

    @PostMapping("/setPhoneNumber")
    public boolean setPhoneNumber(@RequestBody Map<String, String> map){
        return clientService.savePhoneNumber(map);
    }

}
