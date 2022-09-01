package com.bootmcamp.accountbankservice.service.impl;

import com.bootmcamp.accountbankservice.entity.FixedTermAccountBank;
import com.bootmcamp.accountbankservice.repository.FixedTermAccountBankRepository;
import com.bootmcamp.accountbankservice.service.FixedTermAccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FixedTermAccountBankServiceImpl implements FixedTermAccountBankService
{
    @Autowired
    private FixedTermAccountBankRepository fixedTermAccountBankRepository;

    @Override
    public Flux<FixedTermAccountBank> findAll()
    {
        return fixedTermAccountBankRepository.findAll();
    }

    @Override
    public Mono<FixedTermAccountBank> findById(String Id)
    {
        return fixedTermAccountBankRepository.findById(Id);
    }

    @Override
    public Mono<FixedTermAccountBank> save(FixedTermAccountBank fixedTermAccountBank)
    {
        return fixedTermAccountBankRepository.save(fixedTermAccountBank);
    }

    @Override
    public Mono<Void> delete(FixedTermAccountBank fixedTermAccountBank) {
        return fixedTermAccountBankRepository.delete(fixedTermAccountBank);
    }
}
