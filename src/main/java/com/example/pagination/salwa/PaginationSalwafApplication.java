package com.example.pagination.salwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PaginationSalwafApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaginationSalwafApplication.class, args);
	}

}
