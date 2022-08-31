package com.bootmcamp.accountbankservice.service;

import com.bootmcamp.accountbankservice.entity.AccountBankType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountBankTypeService
{
    public Flux<AccountBankType> findAll();
    public Mono<AccountBankType> findById(String Id);
    public Mono<AccountBankType> save(AccountBankType accountBankType);
    public Mono<Void> delete(AccountBankType accountBankType);
}
