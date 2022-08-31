package com.bootcamp.customerbankservice.service;

import com.bootcamp.customerbankservice.entity.PersonalCustomerBank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonalCustomerBankService
{
    public Flux<PersonalCustomerBank> findAll();
    public Mono<PersonalCustomerBank> findById(String Id);
    public Mono<Void> delete(PersonalCustomerBank personalCustomerBank);
}
