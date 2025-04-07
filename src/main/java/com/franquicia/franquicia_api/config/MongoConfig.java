package com.franquicia.franquicia_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Configuration
public class MongoConfig {

	@Bean
	public MongoClient reactiveMongoClient() {
		return MongoClients.create("mongodb://localhost:27017");
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
		return new ReactiveMongoTemplate(new SimpleReactiveMongoDatabaseFactory(mongoClient, "franquicia_db"));
	}
}
