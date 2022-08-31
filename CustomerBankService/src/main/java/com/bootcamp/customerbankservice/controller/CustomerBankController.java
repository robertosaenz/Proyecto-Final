package com.bootcamp.customerbankservice.controller;

import com.bootcamp.customerbankservice.dto.CustomerBankDto;
import com.bootcamp.customerbankservice.service.CustomerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerBankController
{
    @Autowired
    private CustomerBankService customerBankService;

    @PostMapping
    public Mono<?> CreateCustomer(@Valid @RequestBody Mono<CustomerBankDto> customerBankDtoMono)
    {
        return customerBankService.saveCustomer(customerBankDtoMono);
    }
    @GetMapping
    public Mono<?> GetCustomerbyId(@RequestParam("customerType") String customerType,@RequestParam("id") String id)
    {
        return customerBankService.getCustomerById(customerType,id);
    }
}
