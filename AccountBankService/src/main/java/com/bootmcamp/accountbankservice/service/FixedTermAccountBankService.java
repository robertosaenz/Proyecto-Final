package com.bootmcamp.accountbankservice.service;

import com.bootmcamp.accountbankservice.entity.FixedTermAccountBank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FixedTermAccountBankService
{
    public Flux<FixedTermAccountBank> findAll();
    public Mono<FixedTermAccountBank> findById(String Id);
    public Mono<FixedTermAccountBank> save(FixedTermAccountBank fixedTermAccountBank);
    public Mono<Void> delete(FixedTermAccountBank fixedTermAccountBank);
}
