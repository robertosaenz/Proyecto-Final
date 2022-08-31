package com.bootcamp.bootcoinbankservice.controller;

import com.bootcamp.bootcoinbankservice.dto.BootcoinBankDto;
import com.bootcamp.bootcoinbankservice.entity.BootcoinBank;
import com.bootcamp.bootcoinbankservice.service.BootcoinBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bootcoinbank")
public class BootcoinBankController
{
    @Autowired
    private BootcoinBankService bootcoinBankService;

    @GetMapping
    public Flux<BootcoinBank> getAll ()
    {
        return bootcoinBankService.findAll();
    }

    @GetMapping("/{Id}")
    public Mono<BootcoinBank> getById(@PathVariable String Id)
    {
        return bootcoinBankService.findById(Id);
    }

    @GetMapping("/phone/{number}")
    public Mono<BootcoinBank> getByPhoneNumber(@PathVariable Long number)
    {
        return bootcoinBankService.findByPhoneNumber(number);
    }

    @PostMapping
    public Mono<BootcoinBank> save(@Valid @RequestBody BootcoinBank bootcoinBank)
    {
        return bootcoinBankService.save(bootcoinBank);
    }

    @PutMapping("/{Id}")
    public Mono<BootcoinBank> update(@PathVariable String Id, @Valid @RequestBody BootcoinBank bootcoinBank)
    {
        return bootcoinBankService.update(Id,bootcoinBank);
    }

    @DeleteMapping("/{Id}")
    public Mono<BootcoinBank> delete(@PathVariable String Id)
    {
        return bootcoinBankService.delete(Id);
    }
}
