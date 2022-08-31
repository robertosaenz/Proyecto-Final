package com.bootmcamp.accountbankservice.repository;

import com.bootmcamp.accountbankservice.entity.AccountTransactionBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountTransactionBankRepository extends ReactiveMongoRepository<AccountTransactionBank,String>
{
}
