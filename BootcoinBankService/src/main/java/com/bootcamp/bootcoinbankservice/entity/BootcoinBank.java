package com.bootcamp.bootcoinbankservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bootcoins")
public class BootcoinBank
{
    @Id
    private String id;
    private String typeDocument;
    private String documenNumbert;
    private Long phoneNumber;
    private String emailAddress;
    private double balance;
}
