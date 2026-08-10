package com.rabbitmq_consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("hello.dlx");
    }

    @Bean
    public Queue queue() {
        return QueueBuilder
                .durable("hello.queue")
                .deadLetterExchange("hello.dlx")
                .deadLetterRoutingKey("dead")
                .build();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("hello");
    }

}
