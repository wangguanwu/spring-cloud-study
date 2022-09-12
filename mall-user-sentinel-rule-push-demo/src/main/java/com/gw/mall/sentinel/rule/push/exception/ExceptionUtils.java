package com.gw.mall.sentinel.rule.push.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuling.common.utils.R;

/**
 * @author guanwu
 * @created 2022/9/12 11:23
 */
public class ExceptionUtils {

    public static R fallback(Integer id, Throwable e) {
        return R.error(-2, "============被异常降级==========");
    }

    public static R handleBlockException(Integer id, BlockException e) {
        return R.error(-1, "===========被限流了=========msg: " + e.getMessage());
    }
}
