package com.bootcamp.customerbankservice.service.impl;

import com.bootcamp.customerbankservice.entity.BusinessCustomerBank;
import com.bootcamp.customerbankservice.repository.BusinessCustomerBankRepository;
import com.bootcamp.customerbankservice.service.BusinessCustomerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BusinessCustomerBankServiceImpl implements BusinessCustomerBankService
{
    @Autowired
    private BusinessCustomerBankRepository businessCustomerBankRepository;

    @Override
    public Flux<BusinessCustomerBank> findAll()
    {
        return businessCustomerBankRepository.findAll();
    }

    @Override
    public Mono<BusinessCustomerBank> findById(String Id)
    {
        return businessCustomerBankRepository.findById(Id);
    }

    @Override
    public Mono<Void> delete(BusinessCustomerBank businessCustomerBank)
    {
        return businessCustomerBankRepository.delete(businessCustomerBank);
    }
}
