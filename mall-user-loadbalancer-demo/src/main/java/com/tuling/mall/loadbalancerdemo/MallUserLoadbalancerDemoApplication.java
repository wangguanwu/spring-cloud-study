package com.tuling.mall.loadbalancerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MallUserLoadbalancerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUserLoadbalancerDemoApplication.class, args);
    }

}
