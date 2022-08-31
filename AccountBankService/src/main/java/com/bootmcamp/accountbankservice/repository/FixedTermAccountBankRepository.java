package com.bootmcamp.accountbankservice.repository;

import com.bootmcamp.accountbankservice.entity.FixedTermAccountBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FixedTermAccountBankRepository extends ReactiveMongoRepository<FixedTermAccountBank,String>
{
}
