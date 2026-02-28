package com.capybara.coffee_house.config;

import com.capybara.coffee_house.services.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class TelegramBotConfig {

    private final TelegramBotProperties telegramBotProperties;

    public TelegramBotConfig(TelegramBotProperties telegramBotProperties) {
        this.telegramBotProperties = telegramBotProperties;
    }

    @Bean
    public TelegramBotsLongPollingApplication tgBotApp(TelegramBot telegramBot) throws TelegramApiException {
        TelegramBotsLongPollingApplication botsApi = new TelegramBotsLongPollingApplication();
        botsApi.registerBot(telegramBotProperties.getToken(), telegramBot);
        return botsApi;
    }

    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(telegramBotProperties.getToken());
    }
}
