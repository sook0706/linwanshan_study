package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    // 测试接口：不连数据库、不登录、不报错
    @GetMapping("/hello")
    public R hello() {
        return R.ok("项目运行成功！Token 功能正常！");
    }
}