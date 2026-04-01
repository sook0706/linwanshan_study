package com.dorm.service;

import com.dorm.entity.RepairOrder;
import com.dorm.mapper.RepairOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RepairOrderService {
    @Autowired
    private RepairOrderMapper repairOrderMapper;

    //提交报修单（原有的）
    public boolean submitRepairOrder(RepairOrder repairOrder) {
        return repairOrderMapper.insertRepairOrder(repairOrder) > 0;
    }

    //学生查看自己的报修单
    public List<RepairOrder> getRepairOrdersByStudentId(String studentId) {
        return repairOrderMapper.selectRepairOrdersByStudentId(studentId);
    }

    //管理员查看所有报修单
    public List<RepairOrder> getAllRepairOrders() {
        return repairOrderMapper.selectAllRepairOrders();
    }

    //管理员修改报修状态
    public boolean updateRepairStatus(Long orderId, Integer status) {
        return repairOrderMapper.updateRepairStatus(orderId, status) > 0;
    }

    //管理员删除报修单
    public boolean deleteRepairOrder(Long orderId) {
        return repairOrderMapper.deleteRepairOrder(orderId) > 0;
    }
}