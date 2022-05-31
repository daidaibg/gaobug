package com.imantou.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EntityScan("com.imantou.platform.entity")
@MapperScan("com.imantou.platform.dao")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.imantou"})
public class PlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}
