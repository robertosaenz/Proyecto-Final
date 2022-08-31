package com.bootmcamp.accountbankservice.service.impl;

import com.bootmcamp.accountbankservice.entity.AccountBankType;
import com.bootmcamp.accountbankservice.repository.AccountBankRepository;
import com.bootmcamp.accountbankservice.repository.AccountBankTypeRepository;
import com.bootmcamp.accountbankservice.service.AccountBankTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountBankTypeServiceImpl implements AccountBankTypeService
{
    @Autowired
    private AccountBankTypeRepository accountBankTypeRepository;

    @Override
    public Flux<AccountBankType> findAll() {
        return accountBankTypeRepository.findAll();
    }

    @Override
    public Mono<AccountBankType> findById(String Id) {
        return accountBankTypeRepository.findById(Id);
    }

    @Override
    public Mono<AccountBankType> save(AccountBankType accountBankType) {
        return accountBankTypeRepository.save(accountBankType);
    }

    @Override
    public Mono<Void> delete(AccountBankType accountBankType) {
        return accountBankTypeRepository.delete(accountBankType);
    }
}
