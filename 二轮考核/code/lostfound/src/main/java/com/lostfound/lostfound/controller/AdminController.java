package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.LostItem;
import com.lostfound.lostfound.entity.Report;
import com.lostfound.lostfound.entity.User;
import com.lostfound.lostfound.service.LostItemService;
import com.lostfound.lostfound.service.ReportService;
import com.lostfound.lostfound.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private LostItemService lostItemService;

    @Autowired
    private ReportService reportService;

    // 1. 用户管理
    /**
     * 查看所有用户
     */
    @GetMapping("/user/list")
    public R getAllUsers(HttpServletRequest request) {
        // 管理员权限验证
        Integer role = (Integer) request.getAttribute("role");
        if (role == null || role != 1) {
            return R.error("无管理员权限");
        }

        List<User> list = userService.list();
        return R.ok(list);
    }

    /**
     * 封禁用户
     */
    @PutMapping("/user/ban/{userId}")
    public R banUser(@PathVariable Long userId, HttpServletRequest request) {
        // 管理员权限验证
        Integer role = (Integer) request.getAttribute("role");
        if (role == null || role != 1) {
            return R.error("无管理员权限");
        }

        User user = userService.getById(userId);
        user.setStatus(1);
        userService.updateById(user);
        return R.ok("用户已封禁");
    }

    /**
     * 解封用户
     */
    @PutMapping("/user/unban/{userId}")
    public R unbanUser(@PathVariable Long userId, HttpServletRequest request) {
        // 管理员权限验证
        Integer role = (Integer) request.getAttribute("role");
        if (role == null || role != 1) {
            return R.error("无管理员权限");
        }

        User user = userService.getById(userId);
        user.setStatus(0);
        userService.updateById(user);
        return R.ok("用户已解封");
    }

    // 2. 失物信息管理
    /**
     * 查看所有失物信息
     */
    @GetMapping("/lostItem/list")
    public R getAllItems(HttpServletRequest request) {
        // 管理员权限验证
        Integer role = (Integer) request.getAttribute("role");
        if (role == null || role != 1) {
            return R.error("无管理员权限");
        }

        List<LostItem> list = lostItemService.list();
        return R.ok(list);
    }

    /**
     * 删除违规失物信息
     */
    @DeleteMapping("/lostItem/delete/{id}")
    public R deleteItem(@PathVariable Long id, HttpServletRequest request) {
        // 管理员权限验证
        Integer role = (Integer) request.getAttribute("role");
        if (role == null || role != 1) {
            return R.error("无管理员权限");
        }

        lostItemService.removeById(id);
        return R.ok("删除成功");
    }

    // 3. 举报管理
    /**
     * 查看所有举报
     */
    @GetMapping("/report/list")
    public R getAllReports(HttpServletRequest request) {
        // 管理员权限验证
        Integer role = (Integer) request.getAttribute("role");
        if (role == null || role != 1) {
            return R.error("无管理员权限");
        }

        List<Report> list = reportService.list();
        return R.ok(list);
    }

    /**
     * 处理举报（已审核）
     */
    @PutMapping("/report/handle/{id}")
    public R handleReport(@PathVariable Long id, HttpServletRequest request) {
        // 管理员权限验证
        Integer role = (Integer) request.getAttribute("role");
        if (role == null || role != 1) {
            return R.error("无管理员权限");
        }

        Report report = reportService.getById(id);
        report.setStatus(1);
        reportService.updateById(report);
        return R.ok("举报已处理");
    }

    // 4. 平台统计
    /**
     * 平台统计：用户数、失物数、举报数
     */
    @GetMapping("/statistics")
    public R statistics(HttpServletRequest request) {
        // 管理员权限验证
        Integer role = (Integer) request.getAttribute("role");
        if (role == null || role != 1) {
            return R.error("无管理员权限");
        }

        long userCount = userService.count();
        long itemCount = lostItemService.count();
        // 已找回数量
        long foundCount = lostItemService.lambdaQuery()
                .eq(LostItem::getStatus, 1) // 1=已找回/已认领
                .count();

        long reportCount = reportService.count();

        // 正确的返回，只有这一个 return
        return R.ok("用户数：" + userCount +
                " 总物品：" + itemCount +
                " 已找回：" + foundCount +
                " 举报数：" + reportCount);
    }

    // AI 总结
    @GetMapping("/aiSummary")
    public R aiSummary() {
        // 模拟AI总结：最多丢失地点、最多丢失物品
        return R.ok("近期丢失最多地点：图书馆；最多丢失物品：校园卡、耳机、钥匙");
    }
}