package com.bootcamp.walletbankservice.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BootcoinWallet")
public class BootcoinWallet
{
    private String id;
    private Long transmitter;
    private Long receiver;
    private double amount;
    private String status;
}
