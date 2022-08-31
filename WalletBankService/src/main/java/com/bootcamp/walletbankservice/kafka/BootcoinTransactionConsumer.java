package com.bootcamp.walletbankservice.kafka;
import com.bootcamp.basedomains.dto.BootcoinTransactionEvent;
import com.bootcamp.walletbankservice.entity.BootcoinWallet;
import com.bootcamp.walletbankservice.repository.BootcoinWalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class BootcoinTransactionConsumer
{
    @Autowired
    private BootcoinWalletRepository bootcoinWalletRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BootcoinTransactionConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(BootcoinTransactionEvent event){
        LOGGER.info(String.format("Transaction event received in wallet service => %s", event.toString()));

//        Mono<BootcoinWallet> bootcoinWalletMono = null;
//
//        Function<BootcoinWallet, BootcoinWallet> updateWallet = (b) ->
//        {
//            b.setId(event.getBootcoinTransactionDto().getId());
//            b.setTransmitter(event.getBootcoinTransactionDto().getTransmitter());
//            b.setReceiver(event.getBootcoinTransactionDto().getReceiver());
//            b.setAmount(event.getBootcoinTransactionDto().getAmount());
//            b.setStatus(event.getBootcoinTransactionDto().getStatus());
//
//            BootcoinWallet bootcoinWallet = bootcoinWalletRepository.save(b).block();
//            return bootcoinWallet;
//        };
//        bootcoinWalletMono.map(updateWallet).subscribe();
        // save the Transaction event into the database
    }
}
