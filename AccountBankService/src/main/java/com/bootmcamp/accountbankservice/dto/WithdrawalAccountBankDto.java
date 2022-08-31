package com.bootmcamp.accountbankservice.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalAccountBankDto
{
    private String accountBankType;
    private String accountBankId;
    private double amount;
}
