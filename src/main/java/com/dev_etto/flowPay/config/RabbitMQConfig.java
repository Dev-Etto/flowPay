package com.dev_etto.flowPay.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  
    public static final String QUEUE_NAME = "transactions.queue";

    @Bean
    public Queue transactionsQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}