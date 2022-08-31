package com.bootcamp.bootcoinbankservice.repository;

import com.bootcamp.bootcoinbankservice.entity.BootcoinBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BootcoinBankRepository extends ReactiveMongoRepository<BootcoinBank,String>
{
    Mono<BootcoinBank> findByPhoneNumber(Long phoneNumberBuyer);
}
