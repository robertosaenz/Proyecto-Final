package com.bootcamp.customerbankservice.controller;

import com.bootcamp.customerbankservice.entity.CustomerBankType;
import com.bootcamp.customerbankservice.entity.PersonalCustomerBank;
import com.bootcamp.customerbankservice.service.PersonalCustomerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/customer/personal")
public class PersonalCustomerBankController
{
    @Autowired
    private PersonalCustomerBankService personalCustomerBankService;

    @GetMapping
    public Mono<ResponseEntity<Flux<PersonalCustomerBank>>> GetAllPersonalCustomer()
    {
        return Mono.just
                (
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(personalCustomerBankService.findAll())
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PersonalCustomerBank>> GetPersonalCustomer(@PathVariable String id)
    {
        return personalCustomerBankService.findById(id)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{Id}")
    public Mono<ResponseEntity<PersonalCustomerBank>> UpdatePersonalCustomer(@PathVariable String Id, @Valid @RequestBody PersonalCustomerBank personalCustomerBank)
    {
        return personalCustomerBankService.update(Id,personalCustomerBank)
                .map(personalCustomerBank1 ->
                        ResponseEntity
                                .ok().
                                contentType(MediaType.APPLICATION_JSON)
                                .body(personalCustomerBank1))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> DeleteCustomerType(@PathVariable String id)
    {
        return personalCustomerBankService.findById(id).flatMap
                (
                        c->
                        {
                            return personalCustomerBankService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                        }
                ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<PersonalCustomerBank>> SavePersonalCustomer(@Valid @RequestBody PersonalCustomerBank personalCustomerBank)
    {
        return personalCustomerBankService.save(personalCustomerBank)
                .map(personalCustomerBank1 ->
                        ResponseEntity
                                .ok().
                                contentType(MediaType.APPLICATION_JSON)
                                .body(personalCustomerBank1))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
