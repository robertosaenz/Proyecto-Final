package com.bootmcamp.accountbankservice.service;

import com.bootmcamp.accountbankservice.entity.SavingAccountBank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SavingAccountBankService
{
    public Flux<SavingAccountBank> findAll();
    public Mono<SavingAccountBank> findById(String Id);
    public Mono<SavingAccountBank> save(SavingAccountBank savingAccountBank);
    public Mono<Void> delete(SavingAccountBank savingAccountBank);
}
