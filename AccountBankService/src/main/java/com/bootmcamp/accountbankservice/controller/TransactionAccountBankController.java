package com.bootmcamp.accountbankservice.controller;

import com.bootmcamp.accountbankservice.dto.DepositAccountBankDto;
import com.bootmcamp.accountbankservice.dto.ResponseOutput;
import com.bootmcamp.accountbankservice.dto.WithdrawalAccountBankDto;
import com.bootmcamp.accountbankservice.entity.TransactionAccountbank;
import com.bootmcamp.accountbankservice.repository.TransactionAccountBankRepository;
import com.bootmcamp.accountbankservice.service.TransactionAccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accountbank/transaction")
public class TransactionAccountBankController
{
    @Autowired
    private TransactionAccountBankService transactionAccountBankService;
    @Autowired
    private TransactionAccountBankRepository transactionAccountBankRepository;

    @PostMapping("/deposit")
    public Mono<?> saveDeposit(@Valid @RequestBody Mono<DepositAccountBankDto> depositAccountBankDtoMono)
    {
        return transactionAccountBankService.saveDeposit(depositAccountBankDtoMono);
    }
    @PostMapping("/withdrawal")
    public Mono<?> saveWithdrawal(@Valid @RequestBody Mono<WithdrawalAccountBankDto> withdrawalAccountBankDtoMono)
    {
        return transactionAccountBankService.saveWithdrawal(withdrawalAccountBankDtoMono);
    }
    @GetMapping
    public Mono<ResponseEntity<ResponseOutput>> getAllTransaction()
    {
        Flux<TransactionAccountbank> transactionAccountbankFlux = transactionAccountBankService.getAllTransactionBank();

        List<TransactionAccountbank> transactionAccountbankList = transactionAccountbankFlux.collectList().share().block();;

        ResponseOutput responseOutput = new ResponseOutput("Success","Success",new Date(),transactionAccountbankList);

        return Mono.just(
                    ResponseEntity
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(responseOutput))
                .defaultIfEmpty(new ResponseEntity<ResponseOutput>(HttpStatus.NOT_FOUND))
                .onErrorResume(t->
                {
                    return Mono.just(t).cast(WebExchangeBindException.class)
                            .flatMap(e->Mono.just(e.getFieldErrors()))
                            .flatMapMany(Flux::fromIterable)
                            .map(fieldError -> "Field:" + fieldError.getField() + fieldError.getDefaultMessage())
                            .collectList()
                            .flatMap(list ->
                                    {
                                        ResponseOutput responseOutputError = new ResponseOutput("Error","Error",new Date(),list);
                                        return Mono.just(
                                                ResponseEntity
                                                        .badRequest()
                                                        .body(responseOutputError));
                                    }
                            );
                });

//        Flux<TransactionAccountbank> flux = transactionAccountBankService.getAllTransactionBank();
//
//        return flux.collectList().flatMap(d->
//        {
//
//            List<TransactionAccountbank> a = new ArrayList<TransactionAccountbank>();
//            for (int i = 0; i < d.size(); i++)
//            {
//                a.add(new TransactionAccountbank(d.get(i).getId(),d.get(i).getType(),d.get(i).getAmount(),d.get(i).getTransactionDate()));
//            }
//            ResponseOutput responseOutput = new ResponseOutput("Success","Success",new Date(),a);
//            return Mono.just(responseOutput);
//        });
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ResponseOutput>> getTransactionBank(@PathVariable String id)
    {
        return transactionAccountBankService.searchByIdTransactionBank(id).flatMap(transactionAccountBankService->
                {
                    ResponseOutput responseOutput = new ResponseOutput("Success","Success",new Date(),transactionAccountBankService);
                    return Mono.just(
                            ResponseEntity
                                    .ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(responseOutput));
                }).defaultIfEmpty(new ResponseEntity<ResponseOutput>(HttpStatus.NOT_FOUND)).onErrorResume(t->
                {
                    return Mono.just(t).cast(WebExchangeBindException.class)
                            .flatMap(e->Mono.just(e.getFieldErrors()))
                            .flatMapMany(Flux::fromIterable)
                            .map(fieldError -> "Field:" + fieldError.getField() + fieldError.getDefaultMessage())
                            .collectList()
                            .flatMap(list ->
                                    {
                                        ResponseOutput responseOutput = new ResponseOutput("Error","Error",new Date(),list);
                                        return Mono.just(
                                                ResponseEntity
                                                        .badRequest()
                                                        .body(responseOutput));
                                    }
                            );
                });
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ResponseOutput>> updateTransaction(@PathVariable String id, @Valid @RequestBody Mono<TransactionAccountbank> transactionAccountbankMono)
    {
        return transactionAccountBankService.updateTransactionBank(id,transactionAccountbankMono).flatMap(transactionAccountbank ->
        {
            ResponseOutput responseOutput = new ResponseOutput("Success","Success",new Date(),transactionAccountbank);
            return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responseOutput));
        }).defaultIfEmpty(new ResponseEntity<ResponseOutput>(HttpStatus.NOT_FOUND)).onErrorResume(t->
        {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e->Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "Field:" + fieldError.getField() + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list ->
                            {
                                ResponseOutput responseOutput = new ResponseOutput("Error","Error",new Date(),list);
                                return Mono.just(
                                        ResponseEntity
                                                .badRequest()
                                                .body(responseOutput));
                            }
                    );
        });
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ResponseOutput>> deleteTransaction(@PathVariable String id)
    {
        return transactionAccountBankService.deleteTransactionBank(id).flatMap(x->
        {
            ResponseOutput responseOutput = new ResponseOutput("Success","Transaction deleted",new Date(),x);
            return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responseOutput));
        }).defaultIfEmpty(new ResponseEntity<ResponseOutput>(HttpStatus.NOT_FOUND)).onErrorResume(t->
        {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e->Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "Field:" + fieldError.getField() + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list ->
                            {
                                ResponseOutput responseOutput = new ResponseOutput("Error","Critical Error",new Date(),list);
                                return Mono.just(
                                        ResponseEntity
                                                .badRequest()
                                                .body(responseOutput));
                            }
                    );
        });
    }
}
