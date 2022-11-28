package com.woowacourse.kkogkkog.support.config;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import redis.embedded.RedisServer;

@Slf4j
@Profile("test")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        try {
            if (isArmMac()) {
                redisServer = new RedisServer(Objects.requireNonNull(getRedisFileForArcMac()), redisPort);
            }
            if (!isArmMac()) {
                redisServer = new RedisServer(redisPort);
            }

            redisServer.start();
        } catch (Exception e) {
            /**
             * EmbeddedRedis는 Spring Context 변경 시 포트 충돌이 발생
             * 이를 해결하기 위해, 예외가 발생해도 이미 연결이 되었다는 가정하에 예외를 묵살
             * 해당 방법의 문제가 있을 경우, 다음 방법을 고려 -> https://jojoldu.tistory.com/297
             */
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    private boolean isArmMac() {
        return Objects.equals(System.getProperty("os.arch"), "aarch64") &&
            Objects.equals(System.getProperty("os.name"), "Mac OS X");
    }

    private File getRedisFileForArcMac() {
        try {
            return new ClassPathResource("binary/redis/redis-server-6.2.5-mac-arm64").getFile();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
