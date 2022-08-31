package com.bootcamp.bootcoinbankservice.service;

import com.bootcamp.bootcoinbankservice.dto.BootcoinBankDto;
import com.bootcamp.bootcoinbankservice.entity.BootcoinBank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootcoinBankService
{
    public Flux<BootcoinBank> findAll();
    public Mono<BootcoinBank> findByPhoneNumber(Long phoneNumberBuyer);
    public Mono<BootcoinBank> findById(String Id);
    public Mono<BootcoinBank> save(BootcoinBank bootcoinBank);
    public Mono<BootcoinBank> update(String Id, BootcoinBank bootcoinBank);
    public Mono<BootcoinBank> delete(String Id);


}
