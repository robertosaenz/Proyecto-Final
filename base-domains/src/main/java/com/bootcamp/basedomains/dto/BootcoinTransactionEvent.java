package com.bootcamp.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcoinTransactionEvent
{
    private String message;
    private String status;
    private BootcoinTransactionDto bootcoinTransactionDto;
}
