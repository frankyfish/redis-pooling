package com.example.testredis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "mqtt")
class MqttProperties {

    lateinit var clientId: String

}