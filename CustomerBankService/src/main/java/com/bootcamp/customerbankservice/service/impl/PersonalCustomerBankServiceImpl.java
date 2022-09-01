package com.bootcamp.customerbankservice.service.impl;

import com.bootcamp.customerbankservice.entity.PersonalCustomerBank;
import com.bootcamp.customerbankservice.repository.PersonalCustomerBankRepository;
import com.bootcamp.customerbankservice.service.PersonalCustomerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonalCustomerBankServiceImpl implements PersonalCustomerBankService
{
    @Autowired
    private PersonalCustomerBankRepository personalCustomerBankRepository;

    @Override
    public Flux<PersonalCustomerBank> findAll()
    {
        return personalCustomerBankRepository.findAll();
    }

    @Override
    public Mono<PersonalCustomerBank> findById(String Id)
    {
        return personalCustomerBankRepository.findById(Id);
    }

    @Override
    public Mono<PersonalCustomerBank> update(String Id,PersonalCustomerBank personalCustomerBank)
    {
        Mono<PersonalCustomerBank> personalCustomerBankMono = personalCustomerBankRepository.findById(Id);

        return personalCustomerBankMono.flatMap(personalCustomerBank1 ->
        {
            personalCustomerBank1.setName(personalCustomerBank.getName());
            personalCustomerBank1.setIdentDoc(personalCustomerBank.getIdentDoc());
            personalCustomerBank1.setCommonAccountBank(personalCustomerBank.getCommonAccountBank());
            personalCustomerBank1.setFixedTermAccountBank(personalCustomerBank.getFixedTermAccountBank());
            personalCustomerBank1.setSavingAccountBank(personalCustomerBank.getSavingAccountBank());
            return personalCustomerBankRepository.save(personalCustomerBank1);
        });
    }

    @Override
    public Mono<PersonalCustomerBank> save(PersonalCustomerBank personalCustomerBank) {
        return personalCustomerBankRepository.save(personalCustomerBank);
    }

    @Override
    public Mono<Void> delete(PersonalCustomerBank personalCustomerBank) {
        return personalCustomerBankRepository.delete(personalCustomerBank);
    }
}
