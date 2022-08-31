package com.bootmcamp.accountbankservice.service;

import com.bootmcamp.accountbankservice.dto.AccountBankDto;
import com.bootmcamp.accountbankservice.entity.AccountBank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountBankService
{
    public Flux<AccountBank> findAll();
    public Mono<AccountBank> findById(String Id);
    public Mono<AccountBank> save(AccountBank accountBank);
    public Mono<Void> delete(AccountBank accountBank);
    public Mono<?> saveAccountBank(String accounttype,Mono<AccountBankDto>  accountBankRequest);
//    public Mono<?> MovementAccountBank (String accounttype,String movementtype);
}
