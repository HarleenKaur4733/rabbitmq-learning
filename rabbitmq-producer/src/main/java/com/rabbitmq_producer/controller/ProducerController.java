package com.rabbitmq_producer.controller;

import com.rabbitmq_producer.service.ProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public String sendMessage(@RequestBody String message) {

        producerService.sendMessage(message);

        return "Message sent";
    }
}