package com.bootmcamp.accountbankservice.dto;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCustomerBankDto extends CustomerBankDto
{
    private String savingAccountBank;
    private String commonAccountBank;
    private String fixedTermAccountBank;

}
