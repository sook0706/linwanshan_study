package com.dorm.mapper;
import com.dorm.entity.RepairOrder;
import org.apache.ibatis.annotations.Insert;

public interface RepairOrderMapper {
    @Insert("INSERT INTO repair_order (student_id, dorm_num, repair_content) VALUES (#{studentId}, #{dormNum}, #{repairContent})")
    int insertRepairOrder(RepairOrder repairOrder);
}