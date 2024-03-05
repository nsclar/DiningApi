package com.codecademy.DiningApi;

import com.codecademy.DiningApi.model.Restaurant;
import com.codecademy.DiningApi.repository.RestaurantRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DiningApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiningApiApplication.class, args);

	}

}


