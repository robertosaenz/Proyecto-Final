package com.bootmcamp.accountbankservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "TB_FixedTerm_AccountBank")
public class FixedTermAccountBank
{
    @Id
    private String id;
    private double balance;
    private List<String> transactions;
}
