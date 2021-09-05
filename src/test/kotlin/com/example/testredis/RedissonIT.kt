package com.example.testredis

import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.redisson.Redisson
import org.redisson.config.Config
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class RedissonIT {
    var redisConfig: Config = Config()

    // Nothing - Kotlin doesn't like raw type usage
    @Rule
    @JvmField // https://discuss.kotlinlang.org/t/how-can-i-use-rule/304/5
    public val redis: GenericContainer<Nothing> = GenericContainer<Nothing>(DockerImageName.parse("redis:6.2.5-alpine"))
            .withExposedPorts(6379)

    @Before
    fun setUp(): Unit {
        redisConfig.useSingleServer()
                .setAddress("redis://${redis.host}:${redis.firstMappedPort}")
    }

    @Test
    fun testInsert(): Unit {
        val redissonClient = Redisson.create(redisConfig)
        // given
        val bucketName = "clientId#0"
        val bucketValue = "redis-test"
        // and inserted to Redis
        val bucket = redissonClient.getBucket<Any>(bucketName)
        bucket.set(bucketValue)
        // when
        val storedBucket = redissonClient.getBucket<Any>(bucketName)
        // then equals
        Assertions.assertThat(storedBucket.get()).isEqualTo(bucketValue)
    }
}