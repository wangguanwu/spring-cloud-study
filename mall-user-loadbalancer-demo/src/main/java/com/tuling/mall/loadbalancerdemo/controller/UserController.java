package com.tuling.mall.loadbalancerdemo.controller;

import com.tuling.mall.loadbalancerdemo.utils.R;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Fox
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private final RestTemplate restTemplate;

    public UserController(RestTemplate restTemplate, WebClient webClient, ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
        this.lbFunction = lbFunction;
    }

    @RequestMapping(value = "/findOrderByUserId/{id}")
    public R findOrderByUserId(@PathVariable("id") Integer id) {

        String url = "http://mall-order/order/findOrderByUserId/"+id;
        //基于RestTemplate
        R result = restTemplate.getForObject(url,R.class);

        return result;
    }

    private final WebClient webClient;

    @RequestMapping(value = "/findOrderByUserId2/{id}")
    public Mono<R> findOrderByUserIdWithWebClient(@PathVariable("id") Integer id) {

        String url = "http://mall-order/order/findOrderByUserId/"+id;
        //基于WebClient
        Mono<R> result = webClient.get().uri(url)
                .retrieve().bodyToMono(R.class);
        return result;
    }

    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    @RequestMapping(value = "/findOrderByUserId3/{id}")
    public Mono<R> findOrderByUserIdWithWebFlux(@PathVariable("id") Integer id) {

        String url = "http://mall-order/order/findOrderByUserId/"+id;
        //基于WebClient+webFlux
        Mono<R> result = WebClient.builder()
                .filter(lbFunction)
                .build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(R.class);
        return result;
    }

}