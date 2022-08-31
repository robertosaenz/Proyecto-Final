package com.bootcamp.customerbankservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@Data
public class CustomerBank
{
    @Id
    private String id;
    private String name;
    private String identDoc;
}
