package com.gw.mall.sentinel.rule.push.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.gw.mall.sentinel.rule.push.exception.ExceptionUtils;
import com.gw.mall.sentinel.rule.push.feign.OrderFeignService;
import com.tuling.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 
 *
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    OrderFeignService orderFeignService;

    @RequestMapping(value = "/findOrderByUserId/{id}")
    @SentinelResource(value = "findOrderByUserId",
            blockHandlerClass = ExceptionUtils.class,blockHandler = "handleBlockException",
            fallbackClass = ExceptionUtils.class,fallback = "fallback")
    public R  findOrderByUserId(@PathVariable("id") Integer id) {

        //feign调用

        return orderFeignService.findOrderByUserId(id);
    }

    @SentinelResource(value = "hot")
    @RequestMapping("/hot")
    public String hot(String a,String b){
        return a+b;
    }


    AtomicInteger atomicInteger = new AtomicInteger();

    @RequestMapping("/test2")
    public String test2() {
        atomicInteger.getAndIncrement();
        if (atomicInteger.get() % 2 == 0){
            //模拟异常和异常比率
            int i = 1/0;
        }

        return "========test2()";
    }

}
