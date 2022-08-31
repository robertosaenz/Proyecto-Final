package com.bootmcamp.accountbankservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
@EnableEurekaClient
@SpringBootApplication
public class AccountBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountBankServiceApplication.class, args);
	}

}
