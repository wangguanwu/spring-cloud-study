package com.gw.mall.sentinel.rule.push.feign;

import com.tuling.common.utils.R;
import org.springframework.stereotype.Service;

/**
 * @author guanwu
 * @created 2022/9/12 10:46
 */

@Service
public class FallbackOrderFeignService implements OrderFeignService{
    @Override
    public R findOrderByUserId(Integer userId) {
        return R.error(-2, "==============服务降级了===========");
    }
}
