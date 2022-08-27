package com.tuling.mall.sentineldemo.conf;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class MyRequestOriginParser implements RequestOriginParser {


    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getParameter("serviceName");
        log.info("Remote origin {} request api", origin);
        return origin;
    }
}
