package com.bootcamp.walletbankservice.repository;

import com.bootcamp.walletbankservice.entity.BootcoinWallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BootcoinWalletRepository extends ReactiveMongoRepository<BootcoinWallet,String> {
}
