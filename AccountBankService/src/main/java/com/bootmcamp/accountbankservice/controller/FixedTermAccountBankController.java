package com.bootmcamp.accountbankservice.controller;

import com.bootmcamp.accountbankservice.entity.CommonAccountBank;
import com.bootmcamp.accountbankservice.entity.FixedTermAccountBank;
import com.bootmcamp.accountbankservice.service.FixedTermAccountBankService;
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
@RequestMapping("/api/accountbank/fixed")
public class FixedTermAccountBankController
{
    @Autowired
    private FixedTermAccountBankService fixedTermAccountBankService;

    @GetMapping
    public Mono<ResponseEntity<Flux<FixedTermAccountBank>>> GetAllFixedTermAccountBank()
    {
        return Mono.just
                (
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fixedTermAccountBankService.findAll())
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FixedTermAccountBank>> GetFixedTermAccountBank(@PathVariable String id)
    {
        return fixedTermAccountBankService.findById(id)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<FixedTermAccountBank>> UpdateFixedTermAccountBank(@RequestBody FixedTermAccountBank fixedTermAccountBank,@PathVariable String id)
    {
        return fixedTermAccountBankService.findById(id).flatMap
                        (
                                c->
                                {
                                    c.setBalance(fixedTermAccountBank.getBalance());
                                    return fixedTermAccountBankService.save(c);
                                }
                        ).map(c-> ResponseEntity.created(URI.create("/api/accountbank/fixed/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> DeleteFixedTermAccountBank(@PathVariable String id)
    {
        return fixedTermAccountBankService.findById(id).flatMap
                (
                        c->
                        {
                            return fixedTermAccountBankService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                        }
                ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String,Object>>> CreateFixedTermAccountBank(@Valid @RequestBody Mono<FixedTermAccountBank> fixedTermAccountBankMono)
    {
        Map<String,Object> response = new HashMap<>();

        return fixedTermAccountBankMono.flatMap(fixedTermAccountBank ->
        {
            return fixedTermAccountBankService.save(fixedTermAccountBank)
                    .map(c->
                    {
                        response.put("Fixed Term Account",c);
                        response.put("message","Fixed Term Account saved");
                        response.put("timestamp",new Date());
                        return ResponseEntity
                                .created(URI.create("/api/accountbank/fixed/".concat(c.getId())))
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
