package com.example.testredis.config

import com.example.testredis.Constants
import org.redisson.api.RedissonClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RedisClient(val delegate: RedissonClient) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun store(clientId: String) {
        delegate.getSet<String>(Constants.BUCKET_NAME).tryAdd(clientId)
    }

    fun get(key: String): String {
        return delegate.getBucket<String>(key).get()
    }

    fun getAny():String {
        log.info("Set name: ${delegate.getSet<String>(Constants.BUCKET_NAME).name}")
        val clientIdsSet = delegate.getSet<String>(Constants.BUCKET_NAME)
        if (clientIdsSet.isNotEmpty()) {
            return clientIdsSet.removeRandom()
        }
        return ""
    }

}