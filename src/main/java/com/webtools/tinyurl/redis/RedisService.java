package com.webtools.tinyurl.redis;

import com.webtools.tinyurl.domain.TinyUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, TinyUrl> redisTemplate;

    public RedisService(RedisTemplate<String, TinyUrl> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveObjectToCache(String key, TinyUrl value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public Optional<TinyUrl> fetchObjectFromCache(String key) {
        return Optional.ofNullable(this.redisTemplate.opsForValue().get(key));
    }

    public void deleteObjectFromCache(String key) {
        this.redisTemplate.delete(key);
    }
}
