package com.bootmcamp.accountbankservice.client;

import com.bootmcamp.accountbankservice.dto.BusinessCustomerBankDto;
import com.bootmcamp.accountbankservice.dto.PersonalCustomerBankDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerBankClient
{
    private final WebClient webClient;
//    ${customer-bank.uri}
    public CustomerBankClient(@Value("local.com") String url)
    {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<PersonalCustomerBankDto> saveCustomerBank(final PersonalCustomerBankDto personalCustomerBankDto)
    {
        return this.webClient
                .post()
                .uri("customer/")
                .body(Mono.just(personalCustomerBankDto), PersonalCustomerBankDto.class).retrieve()
                .bodyToMono(PersonalCustomerBankDto.class);
    }
    public Mono<BusinessCustomerBankDto> saveCustomerBank(final BusinessCustomerBankDto businessCustomerBankDto)
    {
        return this.webClient
                .post()
                .uri("customer/")
                .body(Mono.just(businessCustomerBankDto), BusinessCustomerBankDto.class).retrieve()
                .bodyToMono(BusinessCustomerBankDto.class);
    }
}
