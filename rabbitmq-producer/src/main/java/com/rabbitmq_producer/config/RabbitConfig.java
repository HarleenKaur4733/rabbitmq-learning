package com.rabbitmq_producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        System.out.println("RabbitAdmin created!");
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("hello.exchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("hello.queue");
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("hello");
    }
}