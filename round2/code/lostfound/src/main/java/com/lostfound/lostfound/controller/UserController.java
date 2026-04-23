package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.User;
import com.lostfound.lostfound.service.UserService;
import com.lostfound.lostfound.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * 接收前端请求，调用service处理业务
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    // 自动注入业务类
    @Autowired
    private UserService userService;

    // 注入JWT工具类
    @Autowired
    private JwtUtil jwtUtil;

    // 注册
    @PostMapping("/register")
    public R register(@RequestBody User user) {
        log.info("用户注册：{}", user.getPhone());
        return userService.register(user);
    }

    // 登录（支持 手机号 / 邮箱）
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        log.info("用户正在登录：{}", user.getPhone());
        return userService.login(user);
    }

    // 查看个人信息
    @GetMapping("/get/{id}")
    public R getById(@PathVariable Long id) {
        return R.ok(userService.getById(id));
    }

    // 修改个人信息（昵称、联系方式、头像、邮箱）
    @PostMapping("/update")
    public R update(@RequestBody User user) {
        log.info("用户修改信息：{}", user.getId());
        userService.updateById(user);
        return R.ok("修改成功");
    }

    // 修改密码
    @PostMapping("/updatePwd")
    public R updatePwd(
            @RequestParam Long userId,
            @RequestParam String oldPwd,
            @RequestParam String newPwd
    ) {
        log.info("用户修改密码：{}", userId);
        return userService.updatePassword(userId, oldPwd, newPwd);
    }

    // 获取当前登录用户信息（前端必须要的，解决404）
    @GetMapping("/info")
    public R info(@RequestHeader("Authorization") String auth) {
        try {
            String token = auth.replace("Bearer ", "");
            Claims claims = jwtUtil.extractClaims(token);
            Long userId = Long.parseLong(claims.get("userId").toString());
            User user = userService.getById(userId);
            return R.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("token无效：" + e.getMessage());
        }
    }
}