package com.bootcamp.walletbankservice.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.lang.annotation.Documented;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("BootcoinWallet")
public class BootcoinWallet implements Serializable
{
    private String id;
    private Long transmitter;
    private Long receiver;
    private double amount;
    private String status;
}
