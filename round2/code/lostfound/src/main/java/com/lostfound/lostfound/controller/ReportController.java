package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.Report;
import com.lostfound.lostfound.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 举报功能控制器
 * 实现用户举报失物信息的接口
 * 对应需求 3.1.2 举报功能
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    // 注入举报服务
    @Autowired
    private ReportService reportService;

    /**
     * 用户提交举报信息
     * @param report 举报内容（物品ID、举报人ID、举报理由）
     * @return 举报结果
     */
    @PostMapping("/add")
    public R addReport(@RequestBody Report report, HttpServletRequest request) {
        // 从Token获取当前登录用户ID（修复数据库报错）
        Long userId = (Long) request.getAttribute("userId");
        report.setUserId(userId);

        // 设置举报时间
        report.setCreateTime(LocalDateTime.now());
        // 状态：0=待管理员审核
        report.setStatus(0);
        // 逻辑删除默认未删除
        report.setIsDelete(0);

        // 保存到数据库
        boolean success = reportService.save(report);

        if (success) {
            return R.ok("举报提交成功，等待管理员审核");
        } else {
            return R.error("举报失败");
        }
    }
}