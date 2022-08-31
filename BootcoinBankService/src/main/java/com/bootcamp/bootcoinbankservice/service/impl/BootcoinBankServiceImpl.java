package com.bootcamp.bootcoinbankservice.service.impl;

import org.modelmapper.ModelMapper;
import com.bootcamp.bootcoinbankservice.dto.BootcoinBankDto;
import com.bootcamp.bootcoinbankservice.entity.BootcoinBank;
import com.bootcamp.bootcoinbankservice.repository.BootcoinBankRepository;
import com.bootcamp.bootcoinbankservice.service.BootcoinBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class BootcoinBankServiceImpl implements BootcoinBankService
{
    @Autowired
    public BootcoinBankRepository bootcoinBankRepository;

    @Override
    public Flux<BootcoinBank> findAll() {
        return bootcoinBankRepository.findAll();
    }

    @Override
    public Mono<BootcoinBank> findByPhoneNumber(Long phoneNumber)
    {
        Mono<BootcoinBank> bootcoinBankMono = bootcoinBankRepository.findByPhoneNumber(phoneNumber);
        return bootcoinBankMono;
    }

    @Override
    public Mono<BootcoinBank> findById(String Id)
    {
        Mono<BootcoinBank> bootcoinBankMono = bootcoinBankRepository.findById(Id);
        return bootcoinBankMono;
    }

    @Override
    public Mono<BootcoinBank> save(BootcoinBank bootcoinBank)
    {
        Mono<BootcoinBank> bootcoinBankMono = bootcoinBankRepository.save(bootcoinBank);
        return bootcoinBankMono;
    }

    public Mono<BootcoinBank> update(String Id, BootcoinBank bootcoinBank)
    {
        Mono<BootcoinBank> bootcoinBankMono = bootcoinBankRepository.findById(Id);
        return bootcoinBankMono.flatMap(bootcoinBank1 ->
        {
            bootcoinBank1.setTypeDocument(bootcoinBank.getTypeDocument());
            bootcoinBank1.setDocumenNumbert(bootcoinBank.getDocumenNumbert());
            bootcoinBank1.setPhoneNumber(bootcoinBank.getPhoneNumber());
            bootcoinBank1.setBalance(bootcoinBank.getBalance());

            return bootcoinBankRepository.save(bootcoinBank1);
        });
    }

    @Override
    public Mono<BootcoinBank> delete(String Id)
    {
        Mono<BootcoinBank> bootcoinBankMono = bootcoinBankRepository.findById(Id);
        return bootcoinBankMono.flatMap(bootcoinBank1 ->
        {
            return bootcoinBankRepository.delete(bootcoinBank1).then(Mono.just(bootcoinBank1));
        });
    }
}
