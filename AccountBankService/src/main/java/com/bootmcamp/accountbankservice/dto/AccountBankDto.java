package com.bootmcamp.accountbankservice.dto;

import com.bootmcamp.accountbankservice.entity.CommonAccountBank;
import com.bootmcamp.accountbankservice.entity.FixedTermAccountBank;
import com.bootmcamp.accountbankservice.entity.SavingAccountBank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBankDto
{
    private CustomerBankDto customerBankDto;
    private List<CommonAccountBank> listCommonAccountBank;
    private CommonAccountBank commonAccountBank;
    private SavingAccountBank savingAccountBank ;
    private FixedTermAccountBank fixedTermAccountBank;
}
