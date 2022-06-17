package com.example.producer.config;

import com.example.producer.config.properties.AppProperties;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private AppProperties appProperties;

    public RabbitMQConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    DirectExchange exchange() {
        return ExchangeBuilder.directExchange(appProperties.getRabbitmq().getExchange()).build();
    }

    @Bean
    Queue xxxQueue() {
        return QueueBuilder.durable(appProperties.getRabbitmq().getQueue())
                .deadLetterExchange(appProperties.getRabbitmq().getExchange())
                .deadLetterRoutingKey(appProperties.getRabbitmq().getQueueRetry())
                .build();
    }

    @Bean
    Queue xxxQueueRetry() {
        return QueueBuilder.durable(appProperties.getRabbitmq().getQueueRetry())
                .deadLetterExchange(appProperties.getRabbitmq().getExchange())
                .deadLetterRoutingKey(appProperties.getRabbitmq().getQueue())
                .ttl(appProperties.getRabbitmq().getTimeToLive())
                .build();
    }

    @Bean
    Binding xxxQueueBinding(Queue xxxQueue, DirectExchange exchange) {
        return BindingBuilder.bind(xxxQueue).to(exchange).withQueueName();
    }

    @Bean
    Binding xxxQueueRetryBinding(Queue xxxQueueRetry, DirectExchange exchange) {
        return BindingBuilder.bind(xxxQueueRetry).to(exchange).withQueueName();
    }

}
