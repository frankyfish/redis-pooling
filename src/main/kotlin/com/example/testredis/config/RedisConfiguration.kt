package com.example.testredis.config

import org.redisson.Redisson
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfiguration {

    @Bean
    fun redisConnection(): RedisClient {
        val config = Config()
        config.useSingleServer()
              .address = "redis://localhost:6379"
        return RedisClient(Redisson.create(config))
    }

}