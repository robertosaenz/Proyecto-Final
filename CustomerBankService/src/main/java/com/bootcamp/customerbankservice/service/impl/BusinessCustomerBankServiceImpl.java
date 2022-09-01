package com.bootcamp.customerbankservice.service.impl;

import com.bootcamp.customerbankservice.entity.BusinessCustomerBank;
import com.bootcamp.customerbankservice.entity.PersonalCustomerBank;
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
    public Mono<BusinessCustomerBank> update(String Id, BusinessCustomerBank businessCustomerBank) {
        Mono<BusinessCustomerBank> businessCustomerBankMono = businessCustomerBankRepository.findById(Id);

        return businessCustomerBankMono.flatMap(businessCustomerBank1 ->
        {
            businessCustomerBank1.setName(businessCustomerBank.getName());
            businessCustomerBank1.setIdentDoc(businessCustomerBank.getIdentDoc());
            businessCustomerBank1.setCommonAccountBank(businessCustomerBank.getCommonAccountBank());
            return businessCustomerBankRepository.save(businessCustomerBank1);
        });
    }

    @Override
    public Mono<BusinessCustomerBank> save(BusinessCustomerBank businessCustomerBank) {
        return businessCustomerBankRepository.save(businessCustomerBank);
    }

    @Override
    public Mono<Void> delete(BusinessCustomerBank businessCustomerBank)
    {
        return businessCustomerBankRepository.delete(businessCustomerBank);
    }
}
