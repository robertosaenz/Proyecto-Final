package com.bootcamp.customerbankservice.service;

import com.bootcamp.customerbankservice.dto.CustomerBankDto;
import reactor.core.publisher.Mono;

public interface CustomerBankService
{
    public Mono<?> saveCustomer(Mono<CustomerBankDto> customerBankDtoMono);
    public Mono<?> getCustomerById(String customerType ,String id);
}
