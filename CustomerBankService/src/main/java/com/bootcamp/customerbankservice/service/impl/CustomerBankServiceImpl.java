package com.bootcamp.customerbankservice.service.impl;

import com.bootcamp.customerbankservice.dto.CustomerBankDto;
import com.bootcamp.customerbankservice.entity.BusinessCustomerBank;
import com.bootcamp.customerbankservice.entity.PersonalCustomerBank;
import com.bootcamp.customerbankservice.repository.BusinessCustomerBankRepository;
import com.bootcamp.customerbankservice.repository.PersonalCustomerBankRepository;
import com.bootcamp.customerbankservice.service.CustomerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class CustomerBankServiceImpl implements CustomerBankService
{
    @Autowired
    private PersonalCustomerBankRepository personalCustomerBankRepository;
    @Autowired
    private BusinessCustomerBankRepository businessCustomerBankRepository;

    @Override
    public Mono<?> saveCustomer(Mono<CustomerBankDto> customerBankDtoMono)
    {
        return customerBankDtoMono.flatMap(e->
        {
            if (e.getCustomerType().equalsIgnoreCase("personal"))
            {
                PersonalCustomerBank personalCustomerBank = new PersonalCustomerBank();
                personalCustomerBank.setName(e.getName());
                personalCustomerBank.setIdentDoc(e.getIdentDoc());
                personalCustomerBank.setSavingAccountBank(e.getSavingAccountBank());
                personalCustomerBank.setCommonAccountBank(e.getCommonAccountBank());
                personalCustomerBank.setFixedTermAccountBank(e.getFixedTermAccountBank());
                return personalCustomerBankRepository.save(personalCustomerBank);
            }
            else if(e.getCustomerType().equalsIgnoreCase("empresa"))
            {
                BusinessCustomerBank businessCustomerBank = new BusinessCustomerBank();
                businessCustomerBank.setName(e.getName());
                businessCustomerBank.setIdentDoc(e.getIdentDoc());
                businessCustomerBank.setCommonAccountBank(e.getListCommonAccountBank());
                return businessCustomerBankRepository.save(businessCustomerBank);
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        });
    }

    @Override
    public Mono<?> getCustomerById(String customerType, String id)
    {
        if (customerType.equalsIgnoreCase("personal"))
        {
            return personalCustomerBankRepository.findById(id);
        }
        else if(customerType.equalsIgnoreCase("empresa"))
        {
            return businessCustomerBankRepository.findById(id);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


}
