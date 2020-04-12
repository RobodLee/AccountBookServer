package com.robod.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 李迪
 * @date 2020/3/7 15:38
 */
@Getter
@Setter
@ToString
public class User implements Serializable {

    private Integer id;
    private String phoneNumber; //手机号
    private String username;    //用户名
    private String password;    //密码

}
