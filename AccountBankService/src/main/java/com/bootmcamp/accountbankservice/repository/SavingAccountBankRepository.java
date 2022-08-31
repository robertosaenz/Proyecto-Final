package com.bootmcamp.accountbankservice.repository;

import com.bootmcamp.accountbankservice.entity.SavingAccountBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SavingAccountBankRepository extends ReactiveMongoRepository<SavingAccountBank,String>
{
}
