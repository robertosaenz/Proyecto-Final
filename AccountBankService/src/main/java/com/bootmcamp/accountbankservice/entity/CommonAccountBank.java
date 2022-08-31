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
@Document(collection = "TB_Common_AccountBank")
public class CommonAccountBank
{
    @Id
    private String id;
    private double balance;
    private List<String> transactions;
}
