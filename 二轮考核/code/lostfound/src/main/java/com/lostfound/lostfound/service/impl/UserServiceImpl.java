package com.lostfound.lostfound.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.User;
import com.lostfound.lostfound.mapper.UserMapper;
import com.lostfound.lostfound.service.UserService;
import com.lostfound.lostfound.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 注册实现
     */
    @Override
    public R register(User user) {
        String phone = user.getPhone();
        String pwd = user.getPassword();

        // 校验1：手机号格式
        if (!phone.matches("1[3-9]\\d{9}")) {
            return R.error("手机号格式不正确");
        }

        // 校验2：密码长度 6-20
        if (pwd.length() < 6 || pwd.length() > 20) {
            return R.error("密码长度必须在6-20位");
        }

        // 判断手机号是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        if (getOne(wrapper) != null) {
            return R.error("手机号已注册");
        }

        // 密码加密
        String md5Pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        user.setPassword(md5Pwd);

        // 自动填充创建时间
        user.setCreateTime(LocalDateTime.now());

        // 逻辑删除默认0
        user.setIsDelete(0);

        // 默认普通用户 0=普通用户 1=管理员
        if (user.getRole() == null) {
            user.setRole(0);
        }

        // 保存
        save(user);

        return R.ok("注册成功");
    }

    /**
     * 登录实现（这里加了TOKEN）
     */
    @Override
    public R login(User user) {
        // 用户输入的账号（可以是手机号或邮箱）
        String account = user.getPhone();

        // 根据手机号或邮箱查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, account).or().eq(User::getEmail, account);
        User dbUser = getOne(wrapper);

        if (dbUser == null) {
            return R.error("用户不存在");
        }

        // 账号状态 1=封禁，禁止登录
        if (dbUser.getStatus() != null && dbUser.getStatus() == 1) {
            return R.error("账号已被管理员封禁，无法登录");
        }

        // 密码加密对比
        String inputPwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if (!inputPwd.equals(dbUser.getPassword())) {
            return R.error("密码错误");
        }

        // token生成
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", dbUser.getId());
        claims.put("role", dbUser.getRole());

        // 生成 JWT TOKEN
        String token = jwtUtil.generateToken(claims);

        // 返回 用户信息 + token
        Map<String, Object> result = new HashMap<>();
        result.put("user", dbUser);
        result.put("token", token);

        return R.ok(result);
    }

    /**
     * 修改密码
     */
    @Override
    public R updatePassword(Long userId, String oldPwd, String newPwd) {
        User user = getById(userId);
        if (user == null) {
            return R.error("用户不存在");
        }

        // 旧密码校验
        String oldMd5 = DigestUtils.md5DigestAsHex(oldPwd.getBytes());
        if (!oldMd5.equals(user.getPassword())) {
            return R.error("原密码错误");
        }

        // 新密码长度校验
        if (newPwd.length() < 6 || newPwd.length() > 20) {
            return R.error("新密码长度 6-20 位");
        }

        // 新密码加密
        String newMd5 = DigestUtils.md5DigestAsHex(newPwd.getBytes());
        user.setPassword(newMd5);
        updateById(user);

        return R.ok("密码修改成功");
    }
}