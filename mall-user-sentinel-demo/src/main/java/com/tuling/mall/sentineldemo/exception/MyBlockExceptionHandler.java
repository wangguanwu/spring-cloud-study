package com.tuling.mall.sentineldemo.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuling.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        log.info("BlockExceptionHandler BlockException===========" + e.getRule());
        R r = null;
        if (e instanceof FlowException) {
            r = R.error(101, "接口限流了");
        } else if (e instanceof DegradeException) {
            r = R.error(102, "熔断了");
        } else if (e instanceof ParamFlowException) {
            r = R.error(103, "热点参数限流了");
        } else if (e instanceof SystemBlockException) {
            r = R.error(104, "触发系统保护规则了");
        } else if (e instanceof AuthorityException) {
            r = R.error(205, "授权规则不通过");
        }
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), r);

    }
}
