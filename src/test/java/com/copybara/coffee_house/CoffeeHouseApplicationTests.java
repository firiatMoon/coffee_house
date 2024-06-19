package com.copybara.coffee_house;

import com.copybara.coffee_house.security.HashPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class CoffeeHouseApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(new HashPasswordEncoder().encode("123"));
	}

}
