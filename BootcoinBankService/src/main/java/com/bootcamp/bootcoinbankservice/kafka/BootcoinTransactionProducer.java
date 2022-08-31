package com.bootcamp.bootcoinbankservice.kafka;

import com.bootcamp.basedomains.dto.BootcoinTransactionEvent;
import com.bootcamp.basedomains.dto.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class BootcoinTransactionProducer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, BootcoinTransactionEvent> kafkaTemplate;

    public BootcoinTransactionProducer(NewTopic topic, KafkaTemplate<String, BootcoinTransactionEvent> kafkaTemplate)
    {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(BootcoinTransactionEvent event){
        LOGGER.info(String.format("Transaction event => %s", event.toString()));

        // create Message
        Message<BootcoinTransactionEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }

}
