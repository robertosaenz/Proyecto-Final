package com.bootcamp.walletbankservice.controller;

import com.bootcamp.walletbankservice.entity.BootcoinWallet;
import com.bootcamp.walletbankservice.repository.BootcoinWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bootcoinwallet")
@EnableCaching
public class BootcoinWalletController
{
    @Autowired
    private BootcoinWalletRepository bootcoinWalletRepository;

    @GetMapping
    public ResponseEntity<List<BootcoinWallet>> getStudents()
    {
        List<BootcoinWallet> students = new ArrayList<>();
        bootcoinWalletRepository.findAll().forEach(students::add);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BootcoinWallet> createStudent(@RequestBody BootcoinWallet bootcoinWallet) {
        BootcoinWallet bootcoinWallet1 = bootcoinWalletRepository.save(bootcoinWallet);
        return new ResponseEntity<>(bootcoinWallet1, HttpStatus.CREATED);
    }

}
