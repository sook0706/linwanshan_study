package com.dorm.controller;

import com.dorm.entity.User;
import com.dorm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 登录接口 → 返回 JSON！！！
    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        String token = userService.login(username, password);
        Map<String, Object> result = new HashMap<>();

        if (token == null) {
            result.put("code", 401);
            result.put("msg", "用户名或密码错误");
            return result;
        }

        // ✅ 正确：返回 JSON 格式，前端能正常解析
        result.put("code", 200);
        result.put("token", token);
        result.put("msg", "登录成功");
        return result;
    }

    // 注册接口
    @PostMapping("/register")
    public Map<String, Object> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String dormNum,
            @RequestParam(required = false) String phone
    ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDormNum(dormNum);
        user.setPhone(phone);

        boolean success = userService.register(user);
        Map<String, Object> result = new HashMap<>();

        if (!success) {
            result.put("code", 400);
            result.put("msg", "用户名已被占用");
            return result;
        }

        result.put("code", 200);
        result.put("msg", "注册成功");
        return result;
    }
}