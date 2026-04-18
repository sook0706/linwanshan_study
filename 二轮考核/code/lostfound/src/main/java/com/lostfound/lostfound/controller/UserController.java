package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.User;
import com.lostfound.lostfound.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // 注册
    @PostMapping("/register")
    public R register(@RequestBody User user) {
        log.info("用户注册：{}", user.getPhone());
        return userService.register(user);
    }

    // 2. 登录（支持 手机号 / 邮箱）
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        log.info("用户正在登录：{}", user.getPhone());
        // 调用原来的登录逻辑
        R result = userService.login(user);

        // 如果登录成功，额外把 角色role 放进返回数据里
        if (result.getCode() == 200) {
            // 拿到登录成功的用户
            User loginUser = (User) result.getData();

            // 直接返回用户，里面包含 role 字段
            return R.ok(loginUser);
        }

        // 登录失败，直接返回错误
        return result;
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
}
