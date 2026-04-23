package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 3.2.3 认领申请模块
 * 功能：发起认领、同意认领、拒绝认领
 */
@RestController
@RequestMapping("/claim")
public class ClaimController {

    // 发起认领申请
    @PostMapping("/apply")
    public R applyClaim() {
        return R.ok("认领申请已提交，等待发布者审核");
    }

    // 同意认领
    @PostMapping("/agree")
    public R agreeClaim() {
        return R.ok("认领成功，双方可联系");
    }

    // 拒绝认领
    @PostMapping("/refuse")
    public R refuseClaim() {
        return R.ok("已拒绝该认领申请");
    }
}