package com.bootcamp.bootcoinbankservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bootcoins_transaction")
public class BootcoinTransaction
{
    @Id
    private String id;
    private Long transmitter;
    private Long receiver;
    private double amount;
    private String status;
}
