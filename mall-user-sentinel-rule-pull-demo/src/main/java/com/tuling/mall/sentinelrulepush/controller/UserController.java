package com.tuling.mall.sentinelrulepush.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.tuling.common.utils.R;
import com.tuling.mall.sentinelrulepush.common.ExceptionUtil;
import com.tuling.mall.sentinelrulepush.feign.OrderFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


/**
 * 
 *
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:53:24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    OrderFeignService orderFeignService;

    @RequestMapping(value = "/findOrderByUserId/{id}")
//    @SentinelResource(value = "findOrderByUserId",
//            blockHandlerClass = ExceptionUtil.class,blockHandler = "handleException",
//            fallbackClass = ExceptionUtil.class,fallback = "fallback")
    public R  findOrderByUserId(@PathVariable("id") Integer id) {
        //feign调用
        R result = orderFeignService.findOrderByUserId(id);
        return result;
    }
    
    

}
