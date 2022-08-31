package com.bootcamp.bootcoinbankservice.service.impl;

import com.bootcamp.basedomains.dto.BootcoinTransactionDto;
import com.bootcamp.basedomains.dto.BootcoinTransactionEvent;
import com.bootcamp.bootcoinbankservice.entity.BootcoinBank;
import com.bootcamp.bootcoinbankservice.entity.BootcoinTransaction;
import com.bootcamp.bootcoinbankservice.kafka.BootcoinTransactionProducer;
import com.bootcamp.bootcoinbankservice.kafka.OrderProducer;
import com.bootcamp.bootcoinbankservice.repository.BootcoinBankRepository;
import com.bootcamp.bootcoinbankservice.repository.BootcoinTransactionRepository;
import com.bootcamp.bootcoinbankservice.service.BootcoinTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@Service
public class BootcoinTransactionServiceImpl implements BootcoinTransactionService
{
    @Autowired
    private BootcoinTransactionRepository bootcoinTransactionRepository;

    @Autowired
    private BootcoinBankRepository bootcoinBankRepository;

    @Autowired
    private BootcoinTransactionProducer bootcoinTransactionProducer;

    @Override
    public Flux<BootcoinTransaction> findAll()
    {
        return bootcoinTransactionRepository.findAll();
    }

    @Override
    public Mono<BootcoinTransaction> findById(String Id)
    {
        Mono<BootcoinTransaction> bootcoinTransactionMono = bootcoinTransactionRepository.findById(Id);
        return bootcoinTransactionMono;
    }

    @Override
    public Mono<BootcoinTransaction> save(BootcoinTransaction bootcoinTransaction)
    {
        Mono<BootcoinTransaction> bootcoinTransactionMono = bootcoinTransactionRepository.save(bootcoinTransaction);
        return bootcoinTransactionMono;
    }

    @Override
    public Mono<BootcoinTransaction> update(String Id, BootcoinTransaction bootcoinTransaction)
    {
        Mono<BootcoinTransaction> bootcoinTransactionMono = bootcoinTransactionRepository.findById(Id);
        return bootcoinTransactionMono.flatMap(bootcoinTransaction1 ->
        {
            bootcoinTransaction1.setAmount(bootcoinTransaction.getAmount());
            return bootcoinTransactionRepository.save(bootcoinTransaction1);
        });
    }

    @Override
    public Mono<BootcoinTransaction> delete(String Id)
    {
        Mono<BootcoinTransaction> bootcoinTransactionMono = bootcoinTransactionRepository.findById(Id);
        return bootcoinTransactionMono.flatMap(bootcoinTransaction ->
        {
            return bootcoinTransactionRepository.delete(bootcoinTransaction).then(Mono.just(bootcoinTransaction));
        });
    }

    @Override
    public Mono<BootcoinTransaction> initTransaction(BootcoinTransaction bootcoinTransaction)
    {
        bootcoinTransaction.setStatus("Pendiente");
        return bootcoinTransactionRepository.insert(bootcoinTransaction).flatMap(bootcoinTransaction1 ->
        {
            BootcoinTransactionDto bootcoinTransactionDto= new BootcoinTransactionDto();
            bootcoinTransactionDto.setId(bootcoinTransaction1.getId());
            bootcoinTransactionDto.setTransmitter(bootcoinTransaction1.getTransmitter());
            bootcoinTransactionDto.setReceiver(bootcoinTransaction1.getReceiver());
            bootcoinTransactionDto.setAmount(bootcoinTransaction1.getAmount());
            bootcoinTransactionDto.setStatus(bootcoinTransaction1.getStatus());

            BootcoinTransactionEvent bootcoinTransactionEvent = new BootcoinTransactionEvent();
            bootcoinTransactionEvent.setStatus("Pendiente");
            bootcoinTransactionEvent.setMessage("Transaction is in pending state");
            bootcoinTransactionEvent.setBootcoinTransactionDto(bootcoinTransactionDto);

            bootcoinTransactionProducer.sendMessage(bootcoinTransactionEvent);
            return Mono.just(bootcoinTransaction1);
        });
    }

    @Override
    public Mono<BootcoinTransaction> finishTransaction(String Id)
    {
        Mono<BootcoinTransaction> bootcoinTransactionMono = bootcoinTransactionRepository.findById(Id);

        Function<BootcoinTransaction, BootcoinTransaction> changeStatus = (c) -> {
            c.setStatus("Completado");
            executeTransaction(c);
            return c;
        };
        return bootcoinTransactionMono.map(changeStatus).flatMap(c->{
            return bootcoinTransactionRepository.save(c);
        });
    }

    public void executeTransaction(BootcoinTransaction bootcoinTransaction)
    {
        BootcoinTransactionDto bootcoinTransactionDto= new BootcoinTransactionDto();
        bootcoinTransactionDto.setId(bootcoinTransaction.getId());
        bootcoinTransactionDto.setTransmitter(bootcoinTransaction.getTransmitter());
        bootcoinTransactionDto.setReceiver(bootcoinTransaction.getReceiver());
        bootcoinTransactionDto.setAmount(bootcoinTransaction.getAmount());
        bootcoinTransactionDto.setStatus(bootcoinTransaction.getStatus());

        BootcoinTransactionEvent bootcoinTransactionEvent = new BootcoinTransactionEvent();
        bootcoinTransactionEvent.setStatus("Completado");
        bootcoinTransactionEvent.setMessage("Transaction is completed");
        bootcoinTransactionEvent.setBootcoinTransactionDto(bootcoinTransactionDto);

        bootcoinTransactionProducer.sendMessage(bootcoinTransactionEvent);

        Mono<BootcoinBank> transmitter = bootcoinBankRepository.findByPhoneNumber(bootcoinTransaction.getTransmitter());
        Function<BootcoinBank, BootcoinBank> updateBalanceTransmitter = (b) ->
        {
            double currentBootcoins = bootcoinTransaction.getAmount();
            b.setBalance(b.getBalance() + currentBootcoins);
            BootcoinBank bootcoinBank = bootcoinBankRepository.save(b).block();
            return bootcoinBank;
        };
        transmitter.map(updateBalanceTransmitter).subscribe();

        Mono<BootcoinBank> receiver = bootcoinBankRepository.findByPhoneNumber(bootcoinTransaction.getReceiver());
        Function<BootcoinBank, BootcoinBank> updateBalanceReceiver = (b) ->
        {
            double currentBootcoins1 = bootcoinTransaction.getAmount();
            b.setBalance(b.getBalance() - currentBootcoins1);
            BootcoinBank bootcoinBank1 = bootcoinBankRepository.save(b).block();
            return bootcoinBank1;
        };
        receiver.map(updateBalanceReceiver).subscribe();
    }

}
