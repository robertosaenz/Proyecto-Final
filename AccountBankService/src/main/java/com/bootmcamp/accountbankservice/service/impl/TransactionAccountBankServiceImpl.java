package com.bootmcamp.accountbankservice.service.impl;

import com.bootmcamp.accountbankservice.dto.DepositAccountBankDto;
import com.bootmcamp.accountbankservice.dto.WithdrawalAccountBankDto;
import com.bootmcamp.accountbankservice.entity.TransactionAccountbank;
import com.bootmcamp.accountbankservice.repository.CommonAccountBankRepository;
import com.bootmcamp.accountbankservice.repository.FixedTermAccountBankRepository;
import com.bootmcamp.accountbankservice.repository.SavingAccountBankRepository;
import com.bootmcamp.accountbankservice.repository.TransactionAccountBankRepository;
import com.bootmcamp.accountbankservice.service.TransactionAccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionAccountBankServiceImpl implements TransactionAccountBankService
{
    @Autowired
    private TransactionAccountBankRepository transactionAccountBankRepository;
    @Autowired
    private SavingAccountBankRepository savingAccountBankRepository;
    @Autowired
    private FixedTermAccountBankRepository fixedTermAccountBankRepository;
    @Autowired
    private CommonAccountBankRepository commonAccountBankRepository;

    @Override
    public Mono<?> saveDeposit(Mono<DepositAccountBankDto> depositAccountBankDtoMono)
    {
        return depositAccountBankDtoMono.flatMap(depositAccountBankDto ->
        {
                TransactionAccountbank transactionAccountbank = new TransactionAccountbank();
                transactionAccountbank.setType("Deposito");
                transactionAccountbank.setAmount(depositAccountBankDto.getAmount());
                transactionAccountbank.setTransactionDate(LocalDate.now().toString());

                Mono<TransactionAccountbank> transactionAccountbankMono = transactionAccountBankRepository.save(transactionAccountbank);

                return transactionAccountbankMono.flatMap(transactionAccountbank1 ->
                {
                    if (depositAccountBankDto.getAccountBankType().equalsIgnoreCase("ahorro"))
                    {
                        return savingAccountBankRepository.findById(depositAccountBankDto.getAccountBankId()).flatMap(savingAccountBank ->
                        {
                            List<String> listTransactions = savingAccountBank.getTransactions();
                            listTransactions.add(transactionAccountbank1.getId());
                            savingAccountBank.setTransactions(listTransactions);
                            savingAccountBank.setBalance(savingAccountBank.getBalance() + depositAccountBankDto.getAmount());
                            return savingAccountBankRepository.save(savingAccountBank);
                        });
                    }

                    else if(depositAccountBankDto.getAccountBankType().equalsIgnoreCase("cuentacorriente"))
                    {
                        return commonAccountBankRepository.findById(depositAccountBankDto.getAccountBankId()).flatMap(commonAccountBank ->
                        {
                            List<String> listTransactions = commonAccountBank.getTransactions();
                            listTransactions.add(transactionAccountbank1.getId());
                            commonAccountBank.setTransactions(listTransactions);
                            commonAccountBank.setBalance(commonAccountBank.getBalance() + depositAccountBankDto.getAmount());
                            return commonAccountBankRepository.save(commonAccountBank);
                        });
                    }
                    else if(depositAccountBankDto.getAccountBankType().equalsIgnoreCase("plazofijo"))
                    {
                        return fixedTermAccountBankRepository.findById(depositAccountBankDto.getAccountBankId()).flatMap(termAccountBank ->
                        {
                            List<String> listTransactions = termAccountBank.getTransactions();
                            listTransactions.add(transactionAccountbank1.getId());
                            termAccountBank.setTransactions(listTransactions);
                            termAccountBank.setBalance(termAccountBank.getBalance() + depositAccountBankDto.getAmount());
                            return fixedTermAccountBankRepository.save(termAccountBank);
                        });
                    }
                    else
                    {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    }
                });
        });
    }
    @Override
    public Mono<?> saveWithdrawal(Mono<WithdrawalAccountBankDto> withdrawalAccountBankDtoMono)
    {
        return withdrawalAccountBankDtoMono.flatMap(withdrawalAccountBankDto ->
        {
            TransactionAccountbank transactionAccountbank = new TransactionAccountbank();
            transactionAccountbank.setType("Retiro");
            transactionAccountbank.setAmount(withdrawalAccountBankDto.getAmount());
            transactionAccountbank.setTransactionDate(LocalDate.now().toString());

            Mono<TransactionAccountbank> transactionAccountbankMono = transactionAccountBankRepository.save(transactionAccountbank);

            return transactionAccountbankMono.flatMap(transactionAccountbank1 ->
            {
                if (withdrawalAccountBankDto.getAccountBankType().equalsIgnoreCase("ahorro"))
                {
                    return savingAccountBankRepository.findById(withdrawalAccountBankDto.getAccountBankId()).flatMap(savingAccountBank ->
                    {
                        List<String> listTransactions = savingAccountBank.getTransactions();
                        listTransactions.add(transactionAccountbank1.getId());
                        savingAccountBank.setTransactions(listTransactions);
                        savingAccountBank.setBalance(savingAccountBank.getBalance() - withdrawalAccountBankDto.getAmount());
                        return savingAccountBankRepository.save(savingAccountBank);
                    });
                }

                else if(withdrawalAccountBankDto.getAccountBankType().equalsIgnoreCase("cuentacorriente"))
                {
                    return commonAccountBankRepository.findById(withdrawalAccountBankDto.getAccountBankId()).flatMap(commonAccountBank ->
                    {
                        List<String> listTransactions = commonAccountBank.getTransactions();
                        listTransactions.add(transactionAccountbank1.getId());
                        commonAccountBank.setTransactions(listTransactions);
                        commonAccountBank.setBalance(commonAccountBank.getBalance() - withdrawalAccountBankDto.getAmount());
                        return commonAccountBankRepository.save(commonAccountBank);
                    });
                }
                else if(withdrawalAccountBankDto.getAccountBankType().equalsIgnoreCase("plazofijo"))
                {
                    return fixedTermAccountBankRepository.findById(withdrawalAccountBankDto.getAccountBankId()).flatMap(termAccountBank ->
                    {
                        List<String> listTransactions = termAccountBank.getTransactions();
                        listTransactions.add(transactionAccountbank1.getId());
                        termAccountBank.setTransactions(listTransactions);
                        termAccountBank.setBalance(termAccountBank.getBalance() - withdrawalAccountBankDto.getAmount());
                        return fixedTermAccountBankRepository.save(termAccountBank);
                    });
                }
                else
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            });
        });
    }
    @Override
    public Mono<TransactionAccountbank> updateTransactionBank(String id,Mono<TransactionAccountbank> transactionAccountbank)
    {
        Mono<TransactionAccountbank> transactionAccountbankMono = transactionAccountBankRepository.findById(id);

        return transactionAccountbank.flatMap(FLAPMAP_transactionAccountbank1 ->
        {
            TransactionAccountbank OBJ_transactionAccountbank = new TransactionAccountbank();
            OBJ_transactionAccountbank.setType(FLAPMAP_transactionAccountbank1.getType());
            OBJ_transactionAccountbank.setAmount(FLAPMAP_transactionAccountbank1.getAmount());

            return transactionAccountbankMono.flatMap(MAP_transactionAccountbank ->
            {
                MAP_transactionAccountbank.setType(OBJ_transactionAccountbank.getType());
                MAP_transactionAccountbank.setAmount(OBJ_transactionAccountbank.getAmount());
                return transactionAccountBankRepository.save(MAP_transactionAccountbank);
            });

        });
    }
    @Override
    public Mono<TransactionAccountbank> searchByIdTransactionBank(String id)
    {
        Mono<TransactionAccountbank> transactionAccountbankMono = transactionAccountBankRepository.findById(id);
        return transactionAccountbankMono;
    }
    @Override
    public Mono<Boolean> existsTransactionBank(String id) {
        Mono<TransactionAccountbank> transactionAccountbankMono = transactionAccountBankRepository.findById(id);
        Mono<Boolean> boolResult = transactionAccountbankMono.hasElement();
        return boolResult;
    }
    @Override
    public Mono<TransactionAccountbank> deleteTransactionBank(String id)
    {
        Mono<TransactionAccountbank> transactionAccountbankMono = transactionAccountBankRepository.findById(id);
        return transactionAccountbankMono.flatMap(transactionAccountbank ->
        {
            return transactionAccountBankRepository.delete(transactionAccountbank).then(Mono.just(transactionAccountbank));
        });
    }
    @Override
    public Flux<TransactionAccountbank> getAllTransactionBank()
    {
        Flux<TransactionAccountbank> transactionAccountbankMono = transactionAccountBankRepository.findAll();

        return transactionAccountbankMono;
    }

}
