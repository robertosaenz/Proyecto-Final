package com.bootcamp.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcoinTransactionDto
{
    private String id;
    private Long transmitter;
    private Long receiver;
    private double amount;
    private String status;
}
