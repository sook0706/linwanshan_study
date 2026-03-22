package com.dorm.service;
import com.dorm.entity.RepairOrder;
import com.dorm.mapper.RepairOrderMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class RepairOrderService {
    @Resource
    private RepairOrderMapper repairOrderMapper;

    public boolean submitRepairOrder(RepairOrder repairOrder) {
        return repairOrderMapper.insertRepairOrder(repairOrder) == 1;
    }
}