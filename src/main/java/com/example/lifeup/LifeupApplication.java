package com.example.lifeup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LifeupApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeupApplication.class, args);
	}

}
