package com.bootmcamp.accountbankservice.service;

import com.bootmcamp.accountbankservice.dto.DepositAccountBankDto;
import com.bootmcamp.accountbankservice.dto.WithdrawalAccountBankDto;
import com.bootmcamp.accountbankservice.entity.TransactionAccountbank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionAccountBankService
{
    public Mono<?> saveDeposit(Mono<DepositAccountBankDto> depositAccountBankDtoMono);
    public Mono<?> saveWithdrawal(Mono<WithdrawalAccountBankDto> withdrawalAccountBankDtoMono);
    public Mono<TransactionAccountbank> updateTransactionBank(String id,Mono<TransactionAccountbank> transactionAccountbank);
    public Mono<TransactionAccountbank> searchByIdTransactionBank(String id);
    public Mono<Boolean> existsTransactionBank(String id);
    public Mono<TransactionAccountbank>deleteTransactionBank(String id);
    public Flux<TransactionAccountbank> getAllTransactionBank();
}
