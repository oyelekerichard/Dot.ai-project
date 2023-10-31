package com.dot.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DotApplication {
	public static void main(String[] args) {
		SpringApplication.run(DotApplication.class, args);
	}

}
