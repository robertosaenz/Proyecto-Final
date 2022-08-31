package com.bootmcamp.accountbankservice.repository;

import com.bootmcamp.accountbankservice.entity.AccountBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountBankRepository extends ReactiveMongoRepository<AccountBank,String>
{
}
