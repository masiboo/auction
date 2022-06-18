package com.cognizant.product.query.api.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class QueueMessageListener {

    @RabbitListener(queues = "auction_queue")
    public void listener(GenericMessage message) {
        System.out.println(message.getPayload());
    }

}
