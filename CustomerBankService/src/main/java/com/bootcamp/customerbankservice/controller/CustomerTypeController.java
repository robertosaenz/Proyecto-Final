package com.bootcamp.customerbankservice.controller;

import com.bootcamp.customerbankservice.entity.CustomerBankType;
import com.bootcamp.customerbankservice.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customer/customertype")
public class CustomerTypeController
{
    @Autowired
    private CustomerTypeService customerTypeService;

    @GetMapping
    public Mono<ResponseEntity<Flux<CustomerBankType>>> GetAllCustomerType()
    {
        return Mono.just
                (
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(customerTypeService.findAll())
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerBankType>> GetCustomerType(@PathVariable String id)
    {
        return customerTypeService.findById(id)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerBankType>> UpdateCustomerType(@RequestBody CustomerBankType customerType, @PathVariable String id)
    {
        return customerTypeService.findById(id).flatMap
                        (
                                c->
                                {
                                    c.setName(customerType.getName());
                                    return customerTypeService.save(c);
                                }
                        ).map(c-> ResponseEntity.created(URI.create("/api/customer/customertype/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> DeleteCustomerType(@PathVariable String id)
    {
        return customerTypeService.findById(id).flatMap
                (
                        c->
                        {
                            return customerTypeService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                        }
                ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String,Object>>> CreateCustomerType(@Valid @RequestBody Mono<CustomerBankType> customerTypeMono)
    {
        Map<String,Object> response = new HashMap<>();

        return customerTypeMono.flatMap(clientBank -> { return customerTypeService.save(clientBank)
                .map(c->
                {
                    response.put("CustomerType",c);
                    response.put("message","CustomerType saved");
                    response.put("timestamp",new Date());
                    return ResponseEntity
                            .created(URI.create("/api/customer/customertype/".concat(c.getId())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(response);
                });
        }).onErrorResume(t->
        {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e->Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "Field:" + fieldError.getField() + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list ->
                            {
                                response.put("errors",list);
                                response.put("timestamp", new Date());
                                response.put("status", HttpStatus.BAD_REQUEST.value());
                                return Mono.just(ResponseEntity.badRequest().body(response));
                            }
                    );
        });
    }
}

