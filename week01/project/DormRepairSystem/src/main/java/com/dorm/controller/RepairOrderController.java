package com.dorm.controller;

import com.dorm.entity.RepairOrder;
import com.dorm.service.RepairOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
public class RepairOrderController {
    @Resource
    private RepairOrderService repairOrderService;

    @PostMapping("/repair/submit")
    public String submitRepair(
            @RequestParam String studentId,
            @RequestParam String dormNum,
            @RequestParam String repairContent
    ) {
        RepairOrder order = new RepairOrder();
        order.setStudentId(studentId);
        order.setDormNum(dormNum);
        order.setRepairContent(repairContent);
        boolean success = repairOrderService.submitRepairOrder(order);
        return success ? "✅ 报修成功！学号："+studentId+"，宿舍："+dormNum+"，内容："+repairContent : "❌ 提交失败";
    }
}