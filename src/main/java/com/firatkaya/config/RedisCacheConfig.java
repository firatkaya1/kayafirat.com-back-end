package com.firatkaya.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisCacheConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        return template;
    }
    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer()
    {
        return (builder) -> {
            Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
            configurationMap.put("UserToken", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(9)));
            configurationMap.put("User", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(12)));
            configurationMap.put("PostTitle", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(5)));
            configurationMap.put("PostTag", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(5)));
            configurationMap.put("PostId", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(5)));
            builder.withInitialCacheConfigurations(configurationMap);
        };
    }

}
