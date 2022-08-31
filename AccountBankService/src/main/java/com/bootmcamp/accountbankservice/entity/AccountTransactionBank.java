package com.bootmcamp.accountbankservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@Data
@Document("demo_account_transaction_bank")
public class AccountTransactionBank
{
    @Id
    private String id;
    private Double amount;
    private Date date;
    private String concept;
    private AccountBank accountBank;
}
