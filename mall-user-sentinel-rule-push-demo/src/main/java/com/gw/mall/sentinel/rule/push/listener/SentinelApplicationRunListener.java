package com.gw.mall.sentinel.rule.push.listener;

import com.gw.mall.sentinel.rule.push.env.EnvUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author guanwu
 * @created 2022/9/12 10:15
 */
public class SentinelApplicationRunListener implements SpringApplicationRunListener {
    public SentinelApplicationRunListener(SpringApplication springApplication, String []args) {
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        EnvUtils.initEnv(environment);
    }
}
