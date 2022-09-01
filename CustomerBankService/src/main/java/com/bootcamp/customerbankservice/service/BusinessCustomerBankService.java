package com.bootcamp.customerbankservice.service;

import com.bootcamp.customerbankservice.entity.BusinessCustomerBank;
import com.bootcamp.customerbankservice.entity.PersonalCustomerBank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BusinessCustomerBankService
{
    public Flux<BusinessCustomerBank> findAll();
    public Mono<BusinessCustomerBank> findById(String Id);
    public Mono<BusinessCustomerBank> update(String Id, BusinessCustomerBank businessCustomerBank);
    public Mono<BusinessCustomerBank> save(BusinessCustomerBank businessCustomerBank);
    public Mono<Void> delete(BusinessCustomerBank businessCustomerBank);
}
