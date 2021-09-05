package com.example.testredis

import com.example.testredis.service.ClientIdService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
//@EnableConfigurationProperties
class TestRedisApplication

fun main(args: Array<String>) {
    runApplication<TestRedisApplication>(*args)
}
