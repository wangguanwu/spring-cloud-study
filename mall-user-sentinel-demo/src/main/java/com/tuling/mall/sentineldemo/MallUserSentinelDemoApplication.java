package com.tuling.mall.sentineldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author guanwu
 * @created on 2022-08-25 15:15:34
 **/

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MallUserSentinelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUserSentinelDemoApplication.class);
    }
}
