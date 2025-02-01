package com.webtools.tinyurl.redis;

import com.webtools.tinyurl.domain.TinyUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private RedisTemplate<String, TinyUrl> redisTemplate;

    public RedisService(RedisTemplate<String, TinyUrl> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveObjectToCache(String key, TinyUrl value) {
        this.redisTemplate.opsForValue().set(key, value);
        log.info("Cache Miss - saving data to cache");
    }

    public Optional<TinyUrl> fetchObjectFromCache(String key) {
        log.info("Cache Hit - retrieving data from cache");
        return Optional.ofNullable(this.redisTemplate.opsForValue().get(key));
    }

    public void deleteObjectFromCache(String key) {
        this.redisTemplate.delete(key);
        log.info("Cache Evict - data removed from the cache");
    }
}
