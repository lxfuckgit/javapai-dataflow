package com.javapai.dataflow.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class CollectorApplication {
	public static void main(String[] args) {
		SpringApplication.run(CollectorApplication.class, args);
	}
	
	@RequestMapping(value = "/health")
	public String health() {
		return "i'm here!";
	}
}
