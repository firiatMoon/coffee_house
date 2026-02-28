package com.capybara.coffee_house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CoffeeHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeHouseApplication.class, args);
	}

}
