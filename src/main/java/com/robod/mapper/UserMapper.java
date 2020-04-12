package com.robod.mapper;

import com.robod.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author 李迪
 * @date 2020/3/7 11:03
 */
@Component
@Mapper
public interface UserMapper {

    /**
     * 实现用户注册
     * @param user
     * @return
     * @throws Exception
     */
    @Insert("insert into user(phoneNumber,username,password) values (#{phoneNumber},#{username},#{password})")
    public void register(User user) throws Exception;

    /**
     * 用户登录
     * @param user
     * @return
     * @throws Exception
     */
    @Select("SELECT * FROM user WHERE phoneNumber = #{phoneNumber} ")
    public User login(User user) throws Exception;

}
