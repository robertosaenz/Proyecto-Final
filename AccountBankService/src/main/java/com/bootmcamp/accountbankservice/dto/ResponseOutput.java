package com.bootmcamp.accountbankservice.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOutput
{
    public String messageHead;
    public String messageBody;
    public Date date;
    public Object result;

//    public ResponseOutput(String messageHead,String messageBody,Date date,Object result)
//    {
//        this.messageHead = messageHead;
//        this.messageBody = messageBody;
//        this.date = date;
//        this.result = result;
//    }
}
