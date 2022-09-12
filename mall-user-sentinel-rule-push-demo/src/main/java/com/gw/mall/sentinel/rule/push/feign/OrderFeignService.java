package com.gw.mall.sentinel.rule.push.feign;

import com.tuling.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author guanwu
 * @created 2022/9/12 10:44
 */

@FeignClient(value = "mall-order", path="/order", fallback = FallbackOrderFeignService.class)
public interface OrderFeignService {

    @RequestMapping("/findOrderByUserId/{userId}")
    R findOrderByUserId(@PathVariable("userId") Integer userId);
}
