package com.bootcamp.customerbankservice.dto;
import lombok.*;

import java.util.List;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@Data
public class CustomerBankDto
{
    private String name;
    private String identDoc;
    private String savingAccountBank;
    private String commonAccountBank;
    private List<String> listCommonAccountBank;
    private String fixedTermAccountBank;
    private String customerType;
}
