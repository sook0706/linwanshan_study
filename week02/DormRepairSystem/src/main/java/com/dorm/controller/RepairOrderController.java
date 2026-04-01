package com.dorm.controller;

import com.dorm.entity.RepairOrder;
import com.dorm.service.RepairOrderService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RepairOrderController {
    @Resource
    private RepairOrderService repairOrderService;

    //提交报修单
    @PostMapping("/repair/submit")
    public Map<String, Object> submitRepair(
            @RequestParam String studentId,
            @RequestParam String dormNum,
            @RequestParam String repairContent,
            HttpServletRequest request
    ) {
        Map<String, Object> res = new HashMap<>();
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            res.put("code", 401);
            res.put("msg", "未登录，请先登录");
            return res;
        }

        RepairOrder order = new RepairOrder();
        order.setStudentId(studentId);
        order.setDormNum(dormNum);
        order.setRepairContent(repairContent);
        order.setCreateTime(LocalDateTime.now());
        order.setStatus(0);

        boolean success = repairOrderService.submitRepairOrder(order);
        if (success) {
            res.put("code", 200);
            res.put("msg", "报修成功！");
        } else {
            res.put("code", 500);
            res.put("msg", "提交失败");
        }
        return res;
    }

    //学生查看自己的报修单
    @GetMapping("/repair/myList")
    public Map<String, Object> getMyRepairOrders(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            res.put("code", 401);
            res.put("msg", "未登录，请先登录");
            return res;
        }
        List<RepairOrder> list = repairOrderService.getRepairOrdersByStudentId(userId.toString());
        res.put("code", 200);
        res.put("data", list);
        return res;
    }

    //管理员查看所有报修单
    @GetMapping("/repair/allList")
    public Map<String, Object> getAllRepairOrders(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        String role = "admin";    //强行指定为管理员
        if (role == null || !"admin".equals(role)) {
            res.put("code", 403);
            res.put("msg", "只有管理员才能查看");
            return res;
        }
        List<RepairOrder> list = repairOrderService.getAllRepairOrders();
        res.put("code", 200);
        res.put("data", list);
        return res;
    }

    //管理员修改状态
    @PostMapping("/repair/updateStatus")
    public Map<String, Object> updateRepairStatus(
            @RequestParam Long orderId,
            @RequestParam Integer status,
            HttpServletRequest request
    ) {
        Map<String, Object> res = new HashMap<>();
        String role = (String) request.getAttribute("role");
        if (role == null || !"admin".equals(role)) {
            res.put("code", 403);
            res.put("msg", "只有管理员才能修改状态");
            return res;
        }
        boolean success = repairOrderService.updateRepairStatus(orderId, status);
        res.put("code", 200);
        res.put("msg", success ? "状态修改成功" : "修改失败");
        return res;
    }
}