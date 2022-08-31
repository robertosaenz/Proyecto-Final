package com.bootcamp.bootcoinbankservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BootcoinBankDto
{
    private String id;
    @NotEmpty
    private String typeDocument;
    @NotEmpty
    private String documenNumbert;
    @NotEmpty
    private Long phoneNumber;
    @NotEmpty
    private String emailAddress;
    @NotEmpty
    private double balance;
}
