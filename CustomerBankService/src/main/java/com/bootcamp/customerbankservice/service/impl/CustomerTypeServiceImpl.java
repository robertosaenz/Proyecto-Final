package com.bootcamp.customerbankservice.service.impl;

import com.bootcamp.customerbankservice.entity.CustomerBankType;
import com.bootcamp.customerbankservice.repository.CustomerTypeRepository;
import com.bootcamp.customerbankservice.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerTypeServiceImpl implements CustomerTypeService
{
    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Override
    public Flux<CustomerBankType> findAll() {
        return customerTypeRepository.findAll();
    }

    @Override
    public Mono<CustomerBankType> findById(String Id) {
        return customerTypeRepository.findById(Id);
    }

    @Override
    public Mono<CustomerBankType> save(CustomerBankType clientTypeEntity) {
        return customerTypeRepository.save(clientTypeEntity);
    }

    @Override
    public Mono<Void> delete(CustomerBankType clientTypeEntity) {
        return customerTypeRepository.delete(clientTypeEntity);
    }
}
