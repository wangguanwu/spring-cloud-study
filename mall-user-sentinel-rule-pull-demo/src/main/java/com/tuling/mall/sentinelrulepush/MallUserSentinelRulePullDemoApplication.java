package com.tuling.mall.sentinelrulepush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MallUserSentinelRulePullDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUserSentinelRulePullDemoApplication.class, args);
    }

}
