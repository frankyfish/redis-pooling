package com.example.testredis;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.*;

public class RedisIT {
    private String redisHost;
    private int redisPort;
    private Config redisConfig = new Config();

    @Rule
    public GenericContainer redisContainer =
            new GenericContainer(DockerImageName.parse("redis:6.2.5-alpine"))
                    .withExposedPorts(6379);

    @Before
    public void setUp() {
        redisHost = redisContainer.getHost();
        redisPort = redisContainer.getFirstMappedPort();
        System.out.println("POrt==" + redisPort);
        redisConfig.useSingleServer()
                   .setAddress(String.format("redis://%s:%d", redisHost, redisPort));
    }


    @Test
    public void insert() {
        RedissonClient redissonClient = Redisson.create(redisConfig);
        // given
        String bucketName = "clientId#0";
        String bucketValue = "redis-test";
        // and inserted to Redis
        RBucket<Object> bucket = redissonClient.getBucket(bucketName);
        bucket.set(bucketValue);
        // when
        RBucket<Object> storedBucket = redissonClient.getBucket(bucketName);
        // then equals
        assertThat(storedBucket.get()).isEqualTo(bucketValue);
    }

}
