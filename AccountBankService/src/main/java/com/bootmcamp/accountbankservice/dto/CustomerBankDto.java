package com.bootmcamp.accountbankservice.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBankDto
{
    private String name;
    private String identDoc;
    private String customerType;
}
