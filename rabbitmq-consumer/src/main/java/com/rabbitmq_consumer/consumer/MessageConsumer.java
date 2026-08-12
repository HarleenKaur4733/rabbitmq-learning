package com.rabbitmq_consumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @RabbitListener(queues = "hello.queue")
    public void receiveMessage(String message) {

        System.out.println("Received: " + message);

        throw new RuntimeException("Something went wrong!");
    }

}