package com.robod.service.impl;

import com.robod.entity.ResultInfo;
import com.robod.entity.User;
import com.robod.mapper.UserMapper;
import com.robod.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 李迪
 * @date 2020/3/7 15:32
 */
@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultInfo<String> register(User user) throws Exception {
        userMapper.register(user);
        ResultInfo<String> resultInfo = new ResultInfo<>();
        resultInfo.setFlag(true);
        resultInfo.setData("注册成功");
        return resultInfo;
    }

    @Override
    public ResultInfo<User> login(User user) throws Exception {
        User loginUser = userMapper.login(user);
        ResultInfo<User> resultInfo = new ResultInfo<>();
        resultInfo.setFlag(true);
        resultInfo.setData(loginUser);
        return resultInfo;
    }
}
