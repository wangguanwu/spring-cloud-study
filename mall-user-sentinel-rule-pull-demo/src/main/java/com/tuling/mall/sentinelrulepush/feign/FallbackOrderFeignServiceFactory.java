package com.tuling.mall.sentinelrulepush.feign;

import com.tuling.common.utils.R;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Fox
 */
@Component
public class FallbackOrderFeignServiceFactory implements FallbackFactory<OrderFeignService> {
    @Override
    public OrderFeignService create(Throwable throwable) {

        return new OrderFeignService() {
            @Override
            public R findOrderByUserId(Integer userId) {
                return R.error(-1,"=======服务降级了========");
            }
        };
    }
}
