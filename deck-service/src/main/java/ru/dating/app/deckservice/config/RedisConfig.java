package ru.dating.app.deckservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.dating.app.deckservice.payload.ProfileResponse;

import java.util.List;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, List<ProfileResponse>> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, List<ProfileResponse>> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<List<ProfileResponse>> serializer =
                new Jackson2JsonRedisSerializer<>(
                        new ObjectMapper().constructType(
                                new TypeReference<List<ProfileResponse>>() {}.getType()
                        )
                );

        template.setValueSerializer(serializer);
        return template;
    }
}