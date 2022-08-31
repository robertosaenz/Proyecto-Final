package com.bootmcamp.accountbankservice.repository;

import com.bootmcamp.accountbankservice.entity.CommonAccountBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommonAccountBankRepository extends ReactiveMongoRepository<CommonAccountBank,String>
{
}
