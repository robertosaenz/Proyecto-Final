package com.bootcamp.walletbankservice.repository;

import com.bootcamp.walletbankservice.entity.BootcoinWallet;
import org.springframework.data.repository.CrudRepository;

public interface BootcoinWalletRepository extends CrudRepository<BootcoinWallet,String>
{
}
