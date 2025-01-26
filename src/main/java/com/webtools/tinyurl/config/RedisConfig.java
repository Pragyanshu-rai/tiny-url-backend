package com.webtools.tinyurl.config;

import com.webtools.tinyurl.domain.TinyUrl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.user}")
    private String redisUser;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    public String getRedisUser() {
        return redisUser;
    }

    public String getRedisHost() { return redisHost; }

    public int getRedisPort() { return Integer.parseInt(redisPort); }

    public String getRedisPassword() {
        return redisPassword;
    }
    @Bean
    public LettuceConnectionFactory redisStandAloneConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(getRedisHost(), getRedisPort());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(getRedisPassword()));
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, TinyUrl> redisTemplate(@Qualifier("redisStandAloneConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, TinyUrl> redisTemplate = new RedisTemplate<String, TinyUrl>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setDefaultSerializer(redisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(redisSerializer);
        return redisTemplate;
    }
}
