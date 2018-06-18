package com.coffee.coffeehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ApplicationStartUp {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStartUp.class, args);
	}

	

}
