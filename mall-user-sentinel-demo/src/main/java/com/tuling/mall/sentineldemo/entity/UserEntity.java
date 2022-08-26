package com.tuling.mall.sentineldemo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author guanwu
 * @created on 2022-08-26 14:14:54
 **/

@Data
public class UserEntity implements Serializable {
    private Integer id;
    private String username;
    private Integer age;

}
