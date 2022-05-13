package com.imantou.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(value = {"com.imantou.common","com.imantou.auth"})
@SpringBootApplication
public class AuthConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthConsumerApplication.class, args);
    }
}
