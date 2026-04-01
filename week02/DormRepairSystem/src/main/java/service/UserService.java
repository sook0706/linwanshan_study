package com.dorm.service;

import com.dorm.entity.User;
import com.dorm.mapper.UserMapper;
import com.dorm.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;

    // 登录：返回Token
    public String login(String username, String password) {
        User user = userMapper.selectUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null; //用户名或密码错误
        }
        //生成Token，存入用户信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());
        return jwtUtil.generateToken(claims);
    }

    //注册
    public boolean register(User user) {
        //检查用户名是否已存在
        User existUser = userMapper.selectUserByUsername(user.getUsername());
        if (existUser != null) {
            return false; //用户名已被占用
        }
        //默认角色为学生
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("student");
        }
        return userMapper.insertUser(user) > 0;
    }
}