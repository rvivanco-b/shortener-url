package com.upwork.shortenerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class ShortenerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortenerApiApplication.class, args);
    }

}
