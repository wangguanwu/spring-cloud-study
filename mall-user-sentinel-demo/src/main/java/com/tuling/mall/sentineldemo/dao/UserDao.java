package com.tuling.mall.sentineldemo.dao;

import com.tuling.mall.sentineldemo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author guanwu
 * @created on 2022-08-26 14:14:35
 **/

@Mapper
public interface UserDao {

    @Select("select * from t_user where id = #{id}")
    UserEntity getById(Integer id);

}
