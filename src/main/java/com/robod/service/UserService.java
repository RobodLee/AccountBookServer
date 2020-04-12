package com.robod.service;

import com.robod.entity.ResultInfo;
import com.robod.entity.User;

/**
 * @author 李迪
 * @date 2020/3/7 11:03
 */
public interface UserService {

    /**
     * 用户注册
     * @param user
     * @throws Exception
     * @return
     */
    public ResultInfo register(User user) throws Exception;

    /**
     * 用户登录
     * @param user
     * @throws Exception
     * @return
     */
    public ResultInfo login(User user) throws Exception;

}
