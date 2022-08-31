package com.bootcamp.customerbankservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@Data
@Document("CustomerType")
public class CustomerBankType
{
    @Id
    private String id;
    @NotEmpty
    private String name;
}
