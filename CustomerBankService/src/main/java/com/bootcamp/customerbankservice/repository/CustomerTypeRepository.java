package com.bootcamp.customerbankservice.repository;

import com.bootcamp.customerbankservice.entity.CustomerBankType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerTypeRepository extends ReactiveMongoRepository<CustomerBankType,String>
{
}
