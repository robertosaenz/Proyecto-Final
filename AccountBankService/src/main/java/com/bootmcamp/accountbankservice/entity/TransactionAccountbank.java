package com.bootmcamp.accountbankservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("TB_Transaction_AccountBank")
public class TransactionAccountbank
{
    @Id
    private String id;
    private String type;
    private double amount;
    private String transactionDate;
}
