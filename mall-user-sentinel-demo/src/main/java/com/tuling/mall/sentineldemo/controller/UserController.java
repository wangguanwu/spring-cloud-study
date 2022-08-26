package com.tuling.mall.sentineldemo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuling.common.utils.R;
import com.tuling.mall.sentineldemo.entity.UserEntity;
import com.tuling.mall.sentineldemo.feign.OrderFeignService;
import com.tuling.mall.sentineldemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author guanwu
 * @created on 2022-08-26 14:27:59
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final OrderFeignService orderFeignService;

    public UserController(UserService userService, OrderFeignService orderFeignService) {
        this.userService = userService;
        this.orderFeignService = orderFeignService;
    }

    @GetMapping("/findOrderByUserId/{id}")
    public R findOrderByUserId(@PathVariable("id") Integer id) {
        R result = orderFeignService.findOrderByUserId(id);
        return result;
    }

    public R handleException(@PathVariable("id") Integer id,
                             BlockException exception) {
        return R.error(-1, "被限流降级啦");
    }


    public R fallback(@PathVariable("id") Integer id,
                      Throwable e) {
        return R.error(-1, "===被熔断降级拉");
    }

    @GetMapping("/info/{id}")
    @SentinelResource(value = "userinfo", blockHandler = "handleException", fallback = "fallback")
    public R info(@PathVariable("id") Integer id) {
        UserEntity userEntity = userService.getById(id);
        if (id == 4) {
            throw new IllegalArgumentException("异常参数");
        }
        return R.ok(Collections.singletonMap(id.toString(), userEntity));
    }

}
