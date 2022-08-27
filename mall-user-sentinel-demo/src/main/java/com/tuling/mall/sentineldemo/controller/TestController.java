package com.tuling.mall.sentineldemo.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuling.common.utils.R;
import com.tuling.mall.sentineldemo.feign.UserFeignService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/test")
public class TestController {

    private final UserFeignService userFeignService;

    private AtomicInteger atomicInteger = new AtomicInteger();

    public TestController(UserFeignService userFeignService) {
        this.userFeignService = userFeignService;
    }

    @GetMapping("/test3")
    @SentinelResource(value = "test3", blockHandler = "handleBlock", fallback = "fallback")
    public R test3(@RequestParam("id") Integer id) {
        R userById = userFeignService.findUserById(id);
        return userById;
    }

    public R handleBlock(Integer id, BlockException ex) {
        R error = R.error(201, ex.getRule().toString());
        return error;
    }

    public R fallback(Integer id, Throwable ex) {
        R error = R.error(201, ex.getMessage());
        return error;
    }

    @GetMapping("/test1")
    public String test1() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "==============test1=============";
    }

    @GetMapping("/test2")
    public String test2() {
        atomicInteger.getAndIncrement();
        if (atomicInteger.get() % 2 == 0) {
            int fenMu = 0;
            double i = 1 / fenMu;
        }
        return "==============test2=============";
    }

    @GetMapping("/test5")
    @SentinelResource(value = "test5", blockHandler = "handleBlock", fallback = "fallback")
    public R test5(@RequestParam("id") Integer id) {
        R userById = userFeignService.findUserById(id);
        return userById;
    }


    @GetMapping("/test4")
    @SentinelResource(value = "test4", blockHandler = "handleBlock", fallback = "fallback")
    public R test4(@RequestParam("id") Integer id) {
        R userById = userFeignService.findUserById(id);
        return userById;
    }
}
