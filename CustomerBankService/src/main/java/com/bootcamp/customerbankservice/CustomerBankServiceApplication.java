package com.bootcamp.customerbankservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CustomerBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerBankServiceApplication.class, args);
	}

}
