package com.bootmcamp.accountbankservice.repository;

import com.bootmcamp.accountbankservice.entity.TransactionAccountbank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionAccountBankRepository extends ReactiveMongoRepository<TransactionAccountbank,String>
{

}
