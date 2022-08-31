package com.bootcamp.bootcoinbankservice.repository;

import com.bootcamp.bootcoinbankservice.entity.BootcoinTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BootcoinTransactionRepository extends ReactiveMongoRepository<BootcoinTransaction,String>
{
}
