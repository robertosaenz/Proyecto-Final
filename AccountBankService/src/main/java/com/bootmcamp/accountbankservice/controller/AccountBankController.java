package com.bootmcamp.accountbankservice.controller;

import com.bootmcamp.accountbankservice.dto.AccountBankDto;
import com.bootmcamp.accountbankservice.entity.AccountBank;
import com.bootmcamp.accountbankservice.service.AccountBankService;
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
@RequestMapping("/api/accountbank")
public class AccountBankController
{
    @Autowired
    private AccountBankService accountBankService;
    @Autowired
    private AccountBankTypeService accountBankTypeService;

    @GetMapping
    public Mono<ResponseEntity<Flux<AccountBank>>> GetAllAccountBank()
    {
        return Mono.just
                (
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(accountBankService.findAll())
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountBank>> GetAccountBank(@PathVariable String Id)
    {
        return accountBankService.findById(Id)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AccountBank>> UpdateAccountBank(@RequestBody AccountBank accountBank,@PathVariable String id)
    {
        return accountBankService.findById(id).flatMap
                        (
                                c->
                                {
                                    c.setCommission(accountBank.getCommission());
                                    c.setCurrentBalance(accountBank.getCurrentBalance());
                                    c.setMovementQuant(accountBank.getMovementQuant());
                                    c.setMovementLimit(accountBank.getMovementLimit());
                                    c.setAccountBankType(accountBank.getAccountBankType());
                                    return accountBankService.save(c);
                                }
                        ).map(c-> ResponseEntity.created(URI.create("/api/accountbank/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> DeleteAccountBank(@PathVariable String id)
    {
        return accountBankService.findById(id).flatMap
                (
                        c->
                        {
                            return accountBankService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                        }
                ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/dt")
    public Mono<ResponseEntity<Map<String,Object>>> CreateAccountBank(@Valid @RequestBody AccountBank accountBank)
    {
        Map<String,Object> response = new HashMap<>();

        return accountBankService.save(accountBank).map(
                c->
                {
                    response.put("AccountBank",c);
                    response.put("Message","AccountBankType Saved");
                    response.put("timestamp",new Date());
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(response);
                }
                )
                .onErrorResume(t->
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



//        Map<String,Object> response = new HashMap<>();
//
//        Mono<AccountBankType> accountBankType = accountBankTypeService.findById(accountBank.getAccountBankType().getId());
//
//        return accountBankType.hasElement()
//                .map(isNotEmpty -> {
//                    if (isNotEmpty)
//                    {
//                        accountBankType.map(accountBankTypeName->
//                        {
//                            return accountBankTypeName;
//                        });
//
//
//                        response.put("AccountBank",accountBankType);
//                        response.put("Message","AccountBankType Saved");
//                        response.put("timestamp",new Date());
//                        return ResponseEntity.ok()
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(response);
//
//                    }
//                    else
//                    {
//                        response.put("Message","AccountBankType Not Found");
//                        response.put("timestamp",new Date());
//                        return ResponseEntity.ok()
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(response);
//                    }
//                });





//        return accountBankType.map(c->
//        {
//            response.put("AccountBank",c);
//            response.put("message","AccountBank saved");
//            response.put("timestamp",new Date());
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(response);
//        });



//        Map<String,Object> response = new HashMap<>();
//
//        return accountBankMono.flatMap(accountBank -> { return accountBankService.save(accountBank)
//                .map(c->
//                {
//                    response.put("AccountBank",c);
//                    response.put("message","AccountBank saved");
//                    response.put("timestamp",new Date());
//                    return ResponseEntity
//                            .created(URI.create("/api/accountbank/".concat(c.getId())))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .body(response);
//                });
//        }).onErrorResume(t->
//        {
//            return Mono.just(t).cast(WebExchangeBindException.class)
//                    .flatMap(e->Mono.just(e.getFieldErrors()))
//                    .flatMapMany(Flux::fromIterable)
//                    .map(fieldError -> "Field:" + fieldError.getField() + fieldError.getDefaultMessage())
//                    .collectList()
//                    .flatMap(list ->
//                            {
//                                response.put("errors",list);
//                                response.put("timestamp", new Date());
//                                response.put("status", HttpStatus.BAD_REQUEST.value());
//                                return Mono.just(ResponseEntity.badRequest().body(response));
//                            }
//                    );
//        });
    }

    @PostMapping("/{accountype}")
    public Mono<?> saveAccountBank(@PathVariable String accountype,@RequestBody Mono<AccountBankDto> accountBankRequestMono)
    {
        return accountBankService.saveAccountBank(accountype,accountBankRequestMono);
    }


}
