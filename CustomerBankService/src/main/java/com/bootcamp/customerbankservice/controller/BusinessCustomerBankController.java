package com.bootcamp.customerbankservice.controller;

import com.bootcamp.customerbankservice.entity.BusinessCustomerBank;
import com.bootcamp.customerbankservice.entity.PersonalCustomerBank;
import com.bootcamp.customerbankservice.service.BusinessCustomerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer/business")
public class BusinessCustomerBankController
{
    @Autowired
    private BusinessCustomerBankService businessCustomerBankService;

    @GetMapping
    public Mono<ResponseEntity<Flux<BusinessCustomerBank>>> GetAllBusinessCustomer()
    {
        return Mono.just
                (
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(businessCustomerBankService.findAll())
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BusinessCustomerBank>> GetBusinessCustomer(@PathVariable String id)
    {
        return businessCustomerBankService.findById(id)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{Id}")
    public Mono<ResponseEntity<BusinessCustomerBank>> UpdateBusinessCustomer(@PathVariable String Id, @Valid @RequestBody BusinessCustomerBank businessCustomerBank)
    {
        return businessCustomerBankService.update(Id,businessCustomerBank)
                .map(businessCustomerBank1 ->
                        ResponseEntity
                                .ok().
                                contentType(MediaType.APPLICATION_JSON)
                                .body(businessCustomerBank1))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> DeleteBusinessCustomer(@PathVariable String id)
    {
        return businessCustomerBankService.findById(id).flatMap
                (
                        c->
                        {
                            return businessCustomerBankService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                        }
                ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public Mono<ResponseEntity<BusinessCustomerBank>> SaveBusinessCustomer(@Valid @RequestBody BusinessCustomerBank businessCustomerBank)
    {
        return businessCustomerBankService.save(businessCustomerBank)
                .map(businessCustomerBank1 ->
                        ResponseEntity
                                .ok().
                                contentType(MediaType.APPLICATION_JSON)
                                .body(businessCustomerBank1))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
