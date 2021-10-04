package com.example.testredis

import com.example.testredis.service.ClientIdService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class Bootstrapper(@Autowired val clientIdService: ClientIdService) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    private lateinit var mqttClientId: String

    @EventListener(ApplicationStartedEvent::class)
    fun startup() {
        mqttClientId = clientIdService.registerNewClientId()
        log.info("Running with clientId=$mqttClientId")
    }

    @EventListener(ContextClosedEvent::class)
    fun tearDown() {
        log.info("Returning clientId=$mqttClientId back to pool.")
        clientIdService.returnClientIdToPool(mqttClientId)
    }

}