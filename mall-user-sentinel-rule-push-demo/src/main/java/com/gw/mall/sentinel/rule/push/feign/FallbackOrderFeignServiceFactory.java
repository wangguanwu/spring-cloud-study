package com.gw.mall.sentinel.rule.push.feign;

import com.tuling.common.utils.R;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author guanwu
 * @created 2022/9/12 10:48
 */

@Component
public class FallbackOrderFeignServiceFactory implements FallbackFactory<OrderFeignService> {
    @Override
    public OrderFeignService create(Throwable throwable) {
        return (userId) -> R.error(-1, "====服务降级了======msg:" + throwable.getMessage() + "==========");
    }
}
