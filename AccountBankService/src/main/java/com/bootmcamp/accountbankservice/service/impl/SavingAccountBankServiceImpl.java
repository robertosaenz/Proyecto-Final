package com.bootmcamp.accountbankservice.service.impl;

import com.bootmcamp.accountbankservice.entity.SavingAccountBank;
import com.bootmcamp.accountbankservice.repository.SavingAccountBankRepository;
import com.bootmcamp.accountbankservice.service.SavingAccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SavingAccountBankServiceImpl implements SavingAccountBankService
{
    @Autowired
    private SavingAccountBankRepository savingAccountBankRepository;

    @Override
    public Flux<SavingAccountBank> findAll() {
        return savingAccountBankRepository.findAll();
    }

    @Override
    public Mono<SavingAccountBank> findById(String Id) {
        return savingAccountBankRepository.findById(Id);
    }

    @Override
    public Mono<SavingAccountBank> save(SavingAccountBank savingAccountBank) {
        return savingAccountBankRepository.save(savingAccountBank);
    }

    @Override
    public Mono<Void> delete(SavingAccountBank savingAccountBank) {
        return savingAccountBankRepository.delete(savingAccountBank);
    }
}
