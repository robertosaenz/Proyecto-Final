package com.bootcamp.customerbankservice.service;

import com.bootcamp.customerbankservice.entity.CustomerBankType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerTypeService
{
    public Flux<CustomerBankType> findAll();
    public Mono<CustomerBankType> findById(String Id);
    public Mono<CustomerBankType> save(CustomerBankType customerType);
    public Mono<Void> delete(CustomerBankType customerBankType);
}
