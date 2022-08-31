package com.bootmcamp.accountbankservice.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositAccountBankDto
{
    private String accountBankType;
    private String accountBankId;
    private double amount;
}
