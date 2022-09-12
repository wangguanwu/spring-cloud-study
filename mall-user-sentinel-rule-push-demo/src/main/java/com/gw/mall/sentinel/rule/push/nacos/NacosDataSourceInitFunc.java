package com.gw.mall.sentinel.rule.push.nacos;

import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.spi.Spi;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.gw.mall.sentinel.rule.push.env.EnvUtils;

import java.util.List;

/**
 * @author guanwu
 * @created 2022/9/12 10:08
 */

@Spi
public class NacosDataSourceInitFunc implements InitFunc {
    public static final String CONTEXT = "spring.cloud.sentinel.datasource.s1.nacos";
    public static final String SERVER_ADDR = CONTEXT + ".server-addr";
    public static final String DATA_ID = CONTEXT + ".dataId";
    public static final String GROUP_ID = CONTEXT + ".groupId";

    @Override
    public void init() throws Exception {

        WritableDataSource<List<FlowRule>> writableDataSource =
                new NacosWritableDataSource<>(EnvUtils.getProperty(SERVER_ADDR),
                       EnvUtils.getProperty(GROUP_ID),
                       EnvUtils.getProperty(DATA_ID),
                        JSON::toJSONString);

        WritableDataSourceRegistry.registerFlowDataSource(writableDataSource);
    }
}
