package com.dorm.mapper;

import com.dorm.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    //根据用户名查询用户（登录用）
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectUserByUsername(String username);

    //新增用户（注册用）
    @Insert("INSERT INTO user(username, password, role, dorm_num, phone) VALUES(#{username}, #{password}, #{role}, #{dormNum}, #{phone})")
    int insertUser(User user);
}