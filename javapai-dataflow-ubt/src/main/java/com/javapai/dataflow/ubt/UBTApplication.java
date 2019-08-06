package com.javapai.dataflow.ubt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class UBTApplication {
	public static void main(String[] args) {
		SpringApplication.run(UBTApplication.class, args);
	}
}
