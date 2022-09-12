package com.gw.mall.sentinel.rule.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author guanwu
 * @created 2022/9/12 9:48
 */

@EnableFeignClients
@SpringBootApplication
public class MallUserSentinelPushDemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MallUserSentinelPushDemoApplication.class, args);
    }
}
