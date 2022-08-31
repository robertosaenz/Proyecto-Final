package com.bootcamp.bootcoinbankservice.controller;

import com.bootcamp.bootcoinbankservice.entity.BootcoinBank;
import com.bootcamp.bootcoinbankservice.entity.BootcoinTransaction;
import com.bootcamp.bootcoinbankservice.service.BootcoinBankService;
import com.bootcamp.bootcoinbankservice.service.BootcoinTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bootcointransaction")
public class BootcoinTransactionController
{
    @Autowired
    private BootcoinTransactionService bootcoinTransactionService;

   /* CRUD*/

    @GetMapping
    public Flux<BootcoinTransaction> getAll ()
    {
        return bootcoinTransactionService.findAll();
    }

    @GetMapping("/{Id}")
    public Mono<BootcoinTransaction> getById(@PathVariable String Id)
    {
        return bootcoinTransactionService.findById(Id);
    }

    @PostMapping
    public Mono<BootcoinTransaction> save(@Valid @RequestBody BootcoinTransaction bootcoinTransaction)
    {
        return bootcoinTransactionService.save(bootcoinTransaction);
    }

    @PutMapping("/{Id}")
    public Mono<BootcoinTransaction> update(@PathVariable String Id, @Valid @RequestBody BootcoinTransaction bootcoinBank)
    {
        return bootcoinTransactionService.update(Id,bootcoinBank);
    }

    @DeleteMapping("/{Id}")
    public Mono<BootcoinTransaction> delete(@PathVariable String Id)
    {
        return bootcoinTransactionService.delete(Id);
    }

    /* OPERATIONS*/
    @PostMapping("/init")
    public Mono<BootcoinTransaction> initTransaction(@Valid @RequestBody BootcoinTransaction bootcoinTransaction)
    {
        return bootcoinTransactionService.initTransaction(bootcoinTransaction);
    }
    @PostMapping("/finish/{Id}")
    public Mono<BootcoinTransaction> finishTransaction(@PathVariable String Id)
    {
        return bootcoinTransactionService.finishTransaction(Id);
    }
}
