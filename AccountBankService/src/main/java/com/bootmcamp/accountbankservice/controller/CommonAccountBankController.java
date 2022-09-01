package com.bootmcamp.accountbankservice.controller;

import com.bootmcamp.accountbankservice.entity.AccountBankType;
import com.bootmcamp.accountbankservice.entity.CommonAccountBank;
import com.bootmcamp.accountbankservice.service.AccountBankTypeService;
import com.bootmcamp.accountbankservice.service.CommonAccountBankService;
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
@RequestMapping("/api/accountbank/common")
public class CommonAccountBankController
{
    @Autowired
    private CommonAccountBankService commonAccountBankService;

    @GetMapping
    public Mono<ResponseEntity<Flux<CommonAccountBank>>> GetAllCommonAccountBank()
    {
        return Mono.just
                (
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(commonAccountBankService.findAll())
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CommonAccountBank>> GetCommonAccountBank(@PathVariable String id)
    {
        return commonAccountBankService.findById(id)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CommonAccountBank>> UpdateCommonAccountBank(@RequestBody CommonAccountBank commonAccountBank,@PathVariable String id)
    {
        return commonAccountBankService.findById(id).flatMap
                        (
                                c->
                                {
                                    c.setBalance(commonAccountBank.getBalance());
                                    return commonAccountBankService.save(c);
                                }
                        ).map(c-> ResponseEntity.created(URI.create("/api/accountbank/accountbanktype/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> DeleteCommonAccountBank(@PathVariable String id)
    {
        return commonAccountBankService.findById(id).flatMap
                (
                        c->
                        {
                            return commonAccountBankService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                        }
                ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String,Object>>> CreateCommonAccountBank(@Valid @RequestBody Mono<CommonAccountBank> commonAccountBankMono)
    {
        Map<String,Object> response = new HashMap<>();

        return commonAccountBankMono.flatMap(commonAccountBank ->
        {
            return commonAccountBankService.save(commonAccountBank)
                    .map(c->
                    {
                        response.put("Common Account",c);
                        response.put("message","Common Account saved");
                        response.put("timestamp",new Date());
                        return ResponseEntity
                                .created(URI.create("/api/accountbank/cpmmon/".concat(c.getId())))
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
