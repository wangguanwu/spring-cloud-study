package com.tuling.mall.sentinelrulepush.common;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuling.common.utils.R;

/**
 * @author Fox
 */
public class ExceptionUtil {

    public static R fallback(Integer id,Throwable e){
        return R.error(-2,"=========被异常降级啦===");
    }

    public static R handleException(Integer id, BlockException e){
        return R.error(-1,"==========被限流啦===");
    }
}
