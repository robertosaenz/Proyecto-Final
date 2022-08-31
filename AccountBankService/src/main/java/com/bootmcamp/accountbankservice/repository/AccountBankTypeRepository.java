package com.bootmcamp.accountbankservice.repository;

import com.bootmcamp.accountbankservice.entity.AccountBankType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountBankTypeRepository extends ReactiveMongoRepository<AccountBankType,String>
{

}
