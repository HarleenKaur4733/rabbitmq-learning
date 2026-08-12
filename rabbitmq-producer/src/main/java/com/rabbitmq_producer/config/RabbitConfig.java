package com.rabbitmq_producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // ---------------- NORMAL FLOW ----------------

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("hello.exchange");
    }

    // If hello.queue dead-letters a message, send it to hello.dlx using routing key
    // dead.
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

    // ---------------- DEAD LETTER FLOW ----------------

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("hello.dlx");
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue("hello.dlq");
    }

    @Bean
    public Binding deadLetterBinding(
            Queue deadLetterQueue,
            DirectExchange deadLetterExchange) {

        return BindingBuilder
                .bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with("dead");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        template.setConfirmCallback((correlationData, ack, cause) -> {

            if (ack) {
                System.out.println("Publisher ACK: Message accepted by RabbitMQ");
            } else {
                System.out.println("Publisher NACK: " + cause);
            }

        });

        return template;
    }
}