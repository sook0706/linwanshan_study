package com.lostfound.lostfound.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.User;

//用户业务接口，作用：定义用户相关的业务方法（登录、注册等）

public interface UserService extends IService<User> {
    R login(User user);
    R register(User user);
    R updatePassword(Long userId, String oldPwd, String newPwd);
}