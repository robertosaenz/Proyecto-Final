package com.bootcamp.customerbankservice.entity;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document("personal_customerbank")
public class PersonalCustomerBank extends CustomerBank
{
    private String savingAccountBank;
    private String commonAccountBank;
    private String fixedTermAccountBank;
}
