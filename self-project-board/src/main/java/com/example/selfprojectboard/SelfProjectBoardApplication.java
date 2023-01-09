package com.example.selfprojectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SelfProjectBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelfProjectBoardApplication.class, args);
	}

}
