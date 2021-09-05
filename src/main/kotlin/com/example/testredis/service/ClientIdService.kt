package com.example.testredis.service

import com.example.testredis.config.MqttProperties
import com.example.testredis.config.RedisClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientIdService(@Autowired val redisClient: RedisClient,
                      @Autowired val mqttProperties: MqttProperties) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun registerNewClientId():String {
        return redisClient.getAny()
                .ifEmpty {
                    log.info("Empty pool, generating id...")
                    UUID.randomUUID().toString().substring(0, 6)
                }
    }

    fun returnClientIdToPool(clientId: String) {
        log.info("Storing clientId=$clientId")
        redisClient.store(clientId)
    }

}