package com.bootcamp.bootcoinbankservice.service;

import com.bootcamp.bootcoinbankservice.entity.BootcoinTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootcoinTransactionService
{
    public Flux<BootcoinTransaction> findAll();
    public Mono<BootcoinTransaction> findById(String Id);
    public Mono<BootcoinTransaction> save(BootcoinTransaction bootcoinTransaction);
    public Mono<BootcoinTransaction> update(String Id, BootcoinTransaction bootcoinTransaction);
    public Mono<BootcoinTransaction> delete(String Id);
    public Mono<BootcoinTransaction> initTransaction(BootcoinTransaction bootcoinTransaction);
    public Mono<BootcoinTransaction> finishTransaction(String Id);
}
