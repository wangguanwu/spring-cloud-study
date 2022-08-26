package com.tuling.mall.sentineldemo.service;

import com.tuling.mall.sentineldemo.entity.UserEntity;

/**
 * @author guanwu
 * @created on 2022-08-26 14:17:32
 **/
public interface UserService {

    UserEntity getById(Integer id);
}
