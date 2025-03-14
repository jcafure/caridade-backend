package dev.caridadems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaridademsApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "dev01");
		SpringApplication.run(CaridademsApplication.class, args);
	}

}
