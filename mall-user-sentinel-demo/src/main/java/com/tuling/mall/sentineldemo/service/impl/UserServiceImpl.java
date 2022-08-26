package com.tuling.mall.sentineldemo.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuling.mall.sentineldemo.dao.UserDao;
import com.tuling.mall.sentineldemo.entity.UserEntity;
import com.tuling.mall.sentineldemo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author guanwu
 * @created on 2022-08-26 14:21:15
 **/

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @SentinelResource(value = "getUser", blockHandler = "handleException")
    @Override
    public UserEntity getById(Integer id) {
        return userDao.getById(id);
    }

    public UserEntity handleException(Integer id, BlockException ex) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("===被限流降级===");
        return userEntity;

    }
}
