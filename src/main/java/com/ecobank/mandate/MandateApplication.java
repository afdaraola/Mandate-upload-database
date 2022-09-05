package com.ecobank.mandate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ecobank.mandate")
public class MandateApplication {

	public static void main(String[] args) {
		SpringApplication.run(MandateApplication.class, args);
	}

}
