package com.tuling.mall.sentineldemo.feign;


import com.tuling.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "mall-user-sentinel-demo", path = "/user")
public interface UserFeignService {

    @RequestMapping("/info/{id}")
    public R findUserById(@PathVariable("id") Integer userId);
}
