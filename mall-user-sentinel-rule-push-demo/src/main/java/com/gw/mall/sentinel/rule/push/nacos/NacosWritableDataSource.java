package com.gw.mall.sentinel.rule.push.nacos;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author guanwu
 * @created 2022/9/12 10:08
 */
public class NacosWritableDataSource<T> implements WritableDataSource<T> {
    private final String serverAddr;
    private final String groupId;
    private final String dataId;
    private  ConfigService configService;
    private final Converter<T, String> converter;
    private final Lock lock = new ReentrantLock(true);
    private final Properties properties;

    public NacosWritableDataSource(String serverAddr, String groupId, String dataId, Converter<T, String> converter) {
        this.serverAddr = serverAddr;
        this.groupId = groupId;
        this.dataId = dataId;
        this.converter = converter;
        this.properties = NacosWritableDataSource.buildProperties(serverAddr);
        initConfigServer(properties);
    }

    private void initConfigServer(Properties properties) {
        try {
            this.configService = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            throw new RuntimeException("Initialize configService has error. ", e);
        }
    }

    private static Properties buildProperties(String serverAddr) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        return properties;
    }

    @Override
    public void write(T o) throws Exception {
        lock.lock();
        try {
            this.configService.publishConfig(dataId, groupId, this.converter.convert(o), ConfigType.JSON.getType());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() throws Exception {

    }
}
