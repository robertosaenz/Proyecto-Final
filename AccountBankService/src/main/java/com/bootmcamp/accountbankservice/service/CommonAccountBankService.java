package com.bootmcamp.accountbankservice.service;

import com.bootmcamp.accountbankservice.entity.AccountBankType;
import com.bootmcamp.accountbankservice.entity.CommonAccountBank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommonAccountBankService
{
    public Flux<CommonAccountBank> findAll();
    public Mono<CommonAccountBank> findById(String Id);
    public Mono<CommonAccountBank> save(CommonAccountBank commonAccountBank);
    public Mono<Void> delete(CommonAccountBank commonAccountBank);
}
