package com.example.producer.config.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
@Data
public class AppProperties {

    private Rabbitmq rabbitmq;
    private Redis redis;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Rabbitmq {
        private String queue;
        private String queueRetry;
        private String exchange;
        private Integer maxRetry;
        private Integer timeToLive;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Redis {
        private Long timeToLive; // minute unit
    }

}
