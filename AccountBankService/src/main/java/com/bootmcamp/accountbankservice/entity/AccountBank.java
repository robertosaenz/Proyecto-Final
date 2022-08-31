package com.bootmcamp.accountbankservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Data
@Document("TB_AccountBank")
public class AccountBank
{
    @Id
    private String id;
    private Double commission;
    private Double currentBalance;
    private Integer movementQuant;
    private Integer movementLimit;
    private String accountBankType;
}
