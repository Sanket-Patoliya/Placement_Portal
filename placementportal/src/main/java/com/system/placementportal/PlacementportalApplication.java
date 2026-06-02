package com.system.placementportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class PlacementportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlacementportalApplication.class, args);
	}

}
