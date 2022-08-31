package com.bootcamp.customerbankservice.repository;

import com.bootcamp.customerbankservice.entity.PersonalCustomerBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonalCustomerBankRepository extends ReactiveMongoRepository<PersonalCustomerBank,String>
{
}
