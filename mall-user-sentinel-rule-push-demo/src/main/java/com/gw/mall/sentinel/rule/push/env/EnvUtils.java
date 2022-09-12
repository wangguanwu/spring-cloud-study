package com.gw.mall.sentinel.rule.push.env;

import org.springframework.core.env.Environment;

/**
 * @author guanwu
 * @created 2022/9/12 10:09
 */
public class EnvUtils {
    private EnvUtils() {

    }

    static Environment environment;

    public static void initEnv(Environment env){
        EnvUtils.environment = env;
    }

    public static String getProperty(String name) {
        return environment.getProperty(name);
    }


}
