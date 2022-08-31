package com.bootcamp.customerbankservice.repository;

import com.bootcamp.customerbankservice.entity.CustomerBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<CustomerBank,String>
{
}
