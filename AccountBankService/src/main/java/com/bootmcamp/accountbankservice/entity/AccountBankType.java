package com.bootmcamp.accountbankservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Data
@Document("TB_AccountBank_Type")
public class AccountBankType
{
    @Id
    private String id;
    @NotEmpty
    private String name;
}
