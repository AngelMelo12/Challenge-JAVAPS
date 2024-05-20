package com.fiap.challenge.targetcustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TargetcustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TargetcustomerApplication.class, args);
	}

}
