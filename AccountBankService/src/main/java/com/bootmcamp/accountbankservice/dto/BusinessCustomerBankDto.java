package com.bootmcamp.accountbankservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessCustomerBankDto extends CustomerBankDto
{
    private List<String> listCommonAccountBank;
}
