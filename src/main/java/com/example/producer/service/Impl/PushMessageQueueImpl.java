package com.example.producer.service.Impl;

import com.example.producer.config.properties.AppProperties;
import com.example.producer.service.PushMessageQueue;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PushMessageQueueImpl implements PushMessageQueue {

    private final AppProperties appProperties;
    private final AmqpTemplate amqpTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    public PushMessageQueueImpl(AppProperties appProperties, AmqpTemplate amqpTemplate, RedisTemplate<String, String> redisTemplate) {
        this.appProperties = appProperties;
        this.amqpTemplate = amqpTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void pushMessageQueue(String msg) {
        checkRedis(msg);
        try {
            Message message = MessageBuilder
                    .withBody(msg.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            amqpTemplate.convertAndSend(
                    appProperties.getRabbitmq().getExchange(),
                    appProperties.getRabbitmq().getQueue(),
                    message
            );
        } catch (Exception ex) {
            throw ex;
        }

    }

    private void checkRedis(String msg) {
        String key = "KEY_REDIS";
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForValue().set(key, "REDIS DATA: " + msg, appProperties.getRedis().getTimeToLive(), TimeUnit.MINUTES);
        }
    }

}
