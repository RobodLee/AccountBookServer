package com.robod.controller;

import com.robod.entity.ResultInfo;
import com.robod.entity.User;
import com.robod.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李迪
 * @date 2020/3/7 11:02
 * 和用户相关的，比如注册，登录等功能
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public ResultInfo<String> register(User user){
        ResultInfo<String> resultInfo;
        try {
            resultInfo = userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo<>();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }
        System.out.println(resultInfo.toString());
        return resultInfo;
    }

    @GetMapping("/login")
    public ResultInfo<User> login(User user) {
        ResultInfo<User> resultInfo;
        try {
            resultInfo = userService.login(user);
        } catch (Exception e) {
            resultInfo = new ResultInfo<>();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("登录失败");
        }
        return resultInfo;
    }

}
