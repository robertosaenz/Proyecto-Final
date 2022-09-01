package com.bootmcamp.accountbankservice.service.impl;

import com.bootmcamp.accountbankservice.entity.CommonAccountBank;
import com.bootmcamp.accountbankservice.repository.CommonAccountBankRepository;
import com.bootmcamp.accountbankservice.service.CommonAccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommonAccountBankServiceImpl implements CommonAccountBankService
{
    @Autowired
    private CommonAccountBankRepository commonAccountBankRepository;

    @Override
    public Flux<CommonAccountBank> findAll() {
        return commonAccountBankRepository.findAll();
    }

    @Override
    public Mono<CommonAccountBank> findById(String Id) {
        return commonAccountBankRepository.findById(Id);
    }

    @Override
    public Mono<CommonAccountBank> save(CommonAccountBank commonAccountBank) {
        return commonAccountBankRepository.save(commonAccountBank);
    }

    @Override
    public Mono<Void> delete(CommonAccountBank commonAccountBank) {
        return commonAccountBankRepository.delete(commonAccountBank);
    }
}
