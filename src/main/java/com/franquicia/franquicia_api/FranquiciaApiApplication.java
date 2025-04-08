package com.franquicia.franquicia_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class FranquiciaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FranquiciaApiApplication.class, args);
	}

}
