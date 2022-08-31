package com.bootmcamp.accountbankservice.controller;

import com.bootmcamp.accountbankservice.entity.AccountBankType;
import com.bootmcamp.accountbankservice.service.AccountBankTypeService;
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
@RequestMapping("/api/accountbank/accountbanktype")
public class AccountBankTypeController
{
    @Autowired
    private AccountBankTypeService accountBankTypeService;

    @GetMapping
    public Mono<ResponseEntity<Flux<AccountBankType>>> GetAllAccountBankType()
    {
        return Mono.just
                (
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(accountBankTypeService.findAll())
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountBankType>> GetCustomerType(@PathVariable String id)
    {
        return accountBankTypeService.findById(id)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AccountBankType>> UpdateCustomerType(@RequestBody AccountBankType accountBankType,@PathVariable String id)
    {
        return accountBankTypeService.findById(id).flatMap
                        (
                                c->
                                {
                                    c.setName(accountBankType.getName());
                                    return accountBankTypeService.save(c);
                                }
                        ).map(c-> ResponseEntity.created(URI.create("/api/accountbank/accountbanktype/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> DeleteCustomerType(@PathVariable String id)
    {
        return accountBankTypeService.findById(id).flatMap
                (
                        c->
                        {
                            return accountBankTypeService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                        }
                ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String,Object>>> CreateCustomerType(@Valid @RequestBody Mono<AccountBankType> accountBankTypeMono)
    {
        Map<String,Object> response = new HashMap<>();

        return accountBankTypeMono.flatMap(accountBankType -> {
            return accountBankTypeService.save(accountBankType)
                .map(c->
                {
                    response.put("CustomerType",c);
                    response.put("message","CustomerType saved");
                    response.put("timestamp",new Date());
                    return ResponseEntity
                            .created(URI.create("/api/accountbank/accountbanktype/".concat(c.getId())))
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
