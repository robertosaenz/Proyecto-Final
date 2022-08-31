package com.bootcamp.customerbankservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Document("business_customerbank")
public class BusinessCustomerBank extends CustomerBank
{
    private List<String> commonAccountBank;
}
