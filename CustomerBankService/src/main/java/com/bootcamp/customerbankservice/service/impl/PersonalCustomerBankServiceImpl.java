package com.bootcamp.customerbankservice.service.impl;

import com.bootcamp.customerbankservice.entity.PersonalCustomerBank;
import com.bootcamp.customerbankservice.repository.PersonalCustomerBankRepository;
import com.bootcamp.customerbankservice.service.PersonalCustomerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonalCustomerBankServiceImpl implements PersonalCustomerBankService
{
    @Autowired
    private PersonalCustomerBankRepository personalCustomerBankRepository;

    @Override
    public Flux<PersonalCustomerBank> findAll() {
        return personalCustomerBankRepository.findAll();
    }

    @Override
    public Mono<PersonalCustomerBank> findById(String Id) {
        return personalCustomerBankRepository.findById(Id);
    }

    @Override
    public Mono<Void> delete(PersonalCustomerBank personalCustomerBank) {
        return personalCustomerBankRepository.delete(personalCustomerBank);
    }
}
