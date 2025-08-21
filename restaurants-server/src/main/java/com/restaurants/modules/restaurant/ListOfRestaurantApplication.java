package com.restaurants.modules.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ListOfRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListOfRestaurantApplication.class, args);
	}

}
