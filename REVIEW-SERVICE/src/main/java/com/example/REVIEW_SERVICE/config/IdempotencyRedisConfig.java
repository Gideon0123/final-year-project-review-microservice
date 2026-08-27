package com.example.REVIEW_SERVICE.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdempotencyRedisConfig {

    @Bean
    public RedisTemplate<String, IdempotencyRecord> idempotencyRedisTemplate(
            RedisConnectionFactory connectionFactory
    ) {
        RedisTemplate<String, IdempotencyRecord> template = new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);

        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Jackson2JsonRedisSerializer<IdempotencyRecord> serializer =
                new Jackson2JsonRedisSerializer<>(mapper, IdempotencyRecord.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;

    }

}