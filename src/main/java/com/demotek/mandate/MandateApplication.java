package com.demotek.mandate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.demotek.mandate")
public class MandateApplication {

	public static void main(String[] args) {
		SpringApplication.run(MandateApplication.class, args);
	}

}
