package com.bootmcamp.accountbankservice.controller;

import com.bootmcamp.accountbankservice.entity.FixedTermAccountBank;
import com.bootmcamp.accountbankservice.entity.SavingAccountBank;
import com.bootmcamp.accountbankservice.service.SavingAccountBankService;
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
@RequestMapping("/api/accountbank/saving")
public class SavingAccountBankController
{
    @Autowired
    private SavingAccountBankService savingAccountBankService;

    @GetMapping
    public Mono<ResponseEntity<Flux<SavingAccountBank>>> GetAllSavingAccountBank()
    {
        return Mono.just
                (
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(savingAccountBankService.findAll())
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<SavingAccountBank>> GetCSavingAccountBank(@PathVariable String id)
    {
        return savingAccountBankService.findById(id)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<SavingAccountBank>> UpdateSavingAccountBank(@RequestBody SavingAccountBank savingAccountBank,@PathVariable String id)
    {
        return savingAccountBankService.findById(id).flatMap
                        (
                                c->
                                {
                                    c.setBalance(savingAccountBank.getBalance());
                                    return savingAccountBankService.save(c);
                                }
                        ).map(c-> ResponseEntity.created(URI.create("/api/accountbank/saving/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> DeleteSavingAccountBank(@PathVariable String id)
    {
        return savingAccountBankService.findById(id).flatMap
                (
                        c->
                        {
                            return savingAccountBankService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                        }
                ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String,Object>>> CreateSavingAccountBank(@Valid @RequestBody Mono<SavingAccountBank> savingAccountBankMono)
    {
        Map<String,Object> response = new HashMap<>();

        return savingAccountBankMono.flatMap(savingAccountBank ->
        {
            return savingAccountBankService.save(savingAccountBank)
                    .map(c->
                    {
                        response.put("Saving Account",c);
                        response.put("message","Fixed Term Account saved");
                        response.put("timestamp",new Date());
                        return ResponseEntity
                                .created(URI.create("/api/accountbank/saving/".concat(c.getId())))
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
