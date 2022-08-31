package com.bootcamp.customerbankservice.repository;

import com.bootcamp.customerbankservice.entity.BusinessCustomerBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BusinessCustomerBankRepository extends ReactiveMongoRepository<BusinessCustomerBank,String> {
}
