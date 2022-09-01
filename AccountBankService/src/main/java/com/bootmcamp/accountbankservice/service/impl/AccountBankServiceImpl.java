package com.bootmcamp.accountbankservice.service.impl;

import com.bootmcamp.accountbankservice.client.CustomerBankClient;
import com.bootmcamp.accountbankservice.dto.AccountBankDto;
import com.bootmcamp.accountbankservice.dto.BusinessCustomerBankDto;
import com.bootmcamp.accountbankservice.dto.CustomerBankDto;
import com.bootmcamp.accountbankservice.dto.PersonalCustomerBankDto;
import com.bootmcamp.accountbankservice.entity.*;
import com.bootmcamp.accountbankservice.repository.*;
import com.bootmcamp.accountbankservice.service.AccountBankService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.stream.Collectors;
@RefreshScope
@Service
public class AccountBankServiceImpl implements AccountBankService
{
    @Autowired
    private AccountBankRepository accountBankRepository;
    @Autowired
    private AccountBankTypeRepository accountBankTypeRepository;
    @Autowired
    private SavingAccountBankRepository savingAccountBankRepository;
    @Autowired
    private FixedTermAccountBankRepository fixedTermAccountBankRepository;
    @Autowired
    private CommonAccountBankRepository commonAccountBankRepository;
    @Autowired
    private CustomerBankClient customerBankClient;

    @Override
    public Flux<AccountBank> findAll()
    {
        return accountBankRepository.findAll();
    }

    @Override
    public Mono<AccountBank> findById(String Id)
    {
        return accountBankRepository.findById(Id);
    }

    @Override
    public Mono<AccountBank> save(AccountBank accountBank)
    {
        Mono<AccountBankType> accountBankTypeMono =
                accountBankTypeRepository.findById(accountBank.getAccountBankType());

        return accountBankTypeMono
                .flatMap
                        (
                            accountBankType ->
                            {
                                if (accountBankType.getName().equalsIgnoreCase("ahorro"))
                                {
                                    AccountBank accountBank1 = new AccountBank();
                                    accountBank1.setCommission(0.0);
                                    accountBank1.setCurrentBalance(0.0);
                                    accountBank1.setMovementQuant(0);
                                    accountBank1.setMovementLimit(3);
                                    accountBank1.setAccountBankType(accountBank.getAccountBankType());

                                    return accountBankRepository.save(accountBank1);
                                }
                                else if (accountBankType.getName().equalsIgnoreCase("cuenta corriente"))
                                {
                                    AccountBank accountBank1 = new AccountBank();
                                    accountBank1.setCommission(12.0);
                                    accountBank1.setCurrentBalance(0.0);
                                    accountBank1.setMovementQuant(0);
                                    accountBank1.setMovementLimit(-1);
                                    accountBank1.setAccountBankType(accountBank.getAccountBankType());

                                    return accountBankRepository.save(accountBank1);
                                }
                                else if (accountBankType.getName().equalsIgnoreCase("plazo fijo"))
                                {
                                    AccountBank accountBank1 = new AccountBank();
                                    accountBank1.setCommission(0.0);
                                    accountBank1.setCurrentBalance(0.0);
                                    accountBank1.setMovementQuant(0);
                                    accountBank1.setMovementLimit(1);
                                    accountBank1.setAccountBankType(accountBank.getAccountBankType());

                                    return accountBankRepository.save(accountBank1);
                                }
                                else
                                {
                                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                                }
                            }
                        );
    }

    @Override
    public Mono<Void> delete(AccountBank accountBank)
    {
        return accountBankRepository.delete(accountBank);
    }

    @CircuitBreaker(name="customerService",fallbackMethod = "movementServiceFallback")
    @Override
    public Mono<?> saveAccountBank(String accountype,Mono<AccountBankDto> accountBankDtoMono)
    {
        if (accountype.equalsIgnoreCase("ahorro"))
        {
            return accountBankDtoMono.flatMap(accountBankDto ->
            {
                if (accountBankDto.getCustomerBankDto().getCustomerType().equalsIgnoreCase("personal"))
                {
                    CustomerBankDto customerBankDto = accountBankDto.getCustomerBankDto();
                    SavingAccountBank savingAccountBank = accountBankDto.getSavingAccountBank();
                    savingAccountBank.setTransactions(new ArrayList<>());

                    PersonalCustomerBankDto personalCustomerBankDto = new PersonalCustomerBankDto();
                    personalCustomerBankDto.setName(customerBankDto.getName());
                    personalCustomerBankDto.setIdentDoc(customerBankDto.getIdentDoc());

                    return savingAccountBankRepository.insert(savingAccountBank).flatMap(savingAccountBank1 ->
                    {
                        personalCustomerBankDto.setCustomerType("personal");
                        personalCustomerBankDto.setSavingAccountBank(savingAccountBank1.getId());
                        Mono<PersonalCustomerBankDto> personalCustomerBankDtoMono = customerBankClient.saveCustomerBank(personalCustomerBankDto);
                        return personalCustomerBankDtoMono;
                    });

                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            });
        }
        else if (accountype.equalsIgnoreCase("cuentacorriente"))
        {
            return accountBankDtoMono.flatMap(accountBankDto ->
            {
                if (accountBankDto.getCustomerBankDto().getCustomerType().equalsIgnoreCase("personal"))
                {
                    CustomerBankDto customerBankDto = accountBankDto.getCustomerBankDto();
                    CommonAccountBank commonAccountBank = accountBankDto.getCommonAccountBank();
                    commonAccountBank.setTransactions(new ArrayList<>());

                    PersonalCustomerBankDto personalCustomerBankDto = new PersonalCustomerBankDto();
                    personalCustomerBankDto.setName(customerBankDto.getName());
                    personalCustomerBankDto.setIdentDoc(customerBankDto.getIdentDoc());
                    personalCustomerBankDto.setCustomerType(customerBankDto.getCustomerType());

                    return commonAccountBankRepository.insert(commonAccountBank).flatMap(commonAccountBank1 ->
                    {
                        personalCustomerBankDto.setCommonAccountBank(commonAccountBank1.getId());
                        Mono<PersonalCustomerBankDto> personalCustomerBankDtoMono = customerBankClient.saveCustomerBank(personalCustomerBankDto);
                        return personalCustomerBankDtoMono;
                    });

                }
                else if (accountBankDto.getCustomerBankDto().getCustomerType().equalsIgnoreCase("empresa"))
                {
                    CustomerBankDto customerBankDto = accountBankDto.getCustomerBankDto();
                    accountBankDto.getListCommonAccountBank().forEach(ommonAccountBankEach -> ommonAccountBankEach.setTransactions(new ArrayList<>()));

                    BusinessCustomerBankDto businessCustomerBankDto = new BusinessCustomerBankDto();

                    businessCustomerBankDto.setName(customerBankDto.getName());
                    businessCustomerBankDto.setIdentDoc(customerBankDto.getIdentDoc());
                    businessCustomerBankDto.setCustomerType(customerBankDto.getCustomerType());

                    return commonAccountBankRepository.insert(accountBankDto.getListCommonAccountBank()).collectList()
                            .flatMap(commonAccountBanks -> {
                                businessCustomerBankDto.setListCommonAccountBank(
                                        commonAccountBanks.stream().map(commonAccountBank -> commonAccountBank.getId()).collect(Collectors.toList()));
                                Mono<BusinessCustomerBankDto> businessCustomerBankDtoMono = customerBankClient.saveCustomerBank(businessCustomerBankDto);

                                return businessCustomerBankDtoMono;
                            });

                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            });
        }
        else if (accountype.equalsIgnoreCase("plazofijo"))
        {
            return accountBankDtoMono.flatMap(accountBankDto ->
            {
                if (accountBankDto.getCustomerBankDto().getCustomerType().equalsIgnoreCase("personal"))
                {
                    CustomerBankDto customerBankDto = accountBankDto.getCustomerBankDto();
                    FixedTermAccountBank fixedTermAccountBank = accountBankDto.getFixedTermAccountBank();
                    fixedTermAccountBank.setTransactions(new ArrayList<>());

                    PersonalCustomerBankDto personalCustomerBankDto = new PersonalCustomerBankDto();
                    personalCustomerBankDto.setName(customerBankDto.getName());
                    personalCustomerBankDto.setIdentDoc(customerBankDto.getIdentDoc());
                    personalCustomerBankDto.setCustomerType(customerBankDto.getCustomerType());

                    return fixedTermAccountBankRepository.insert(fixedTermAccountBank).flatMap(fixedTermAccountBank1 ->
                    {
                        personalCustomerBankDto.setFixedTermAccountBank(fixedTermAccountBank1.getId());
                        Mono<PersonalCustomerBankDto> personalCustomerBankDtoMono = customerBankClient.saveCustomerBank(personalCustomerBankDto);
                        return personalCustomerBankDtoMono;
                    });

                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            });
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
