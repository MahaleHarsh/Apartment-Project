package com.example.apartmentservice;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class ApartmentServiceApplication {

	 public static void main(String[] args) {
	        SpringApplication.run(ApartmentServiceApplication.class, args);
	    }
}
