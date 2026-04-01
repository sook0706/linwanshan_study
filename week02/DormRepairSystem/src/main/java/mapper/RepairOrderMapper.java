package com.dorm.mapper;

import com.dorm.entity.RepairOrder;
import org.apache.ibatis.annotations.*;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RepairOrderMapper {

    //提交报修单（原有的）
    @Insert("INSERT INTO repair_order (student_id, dorm_num, repair_content, create_time, status, img_url) " +
            "VALUES(#{studentId}, #{dormNum}, #{repairContent}, #{createTime}, #{status}, #{imgUrl})")
    int insertRepairOrder(RepairOrder repairOrder);

    //学生查询自己的报修单
    @Select("SELECT * FROM repair_order WHERE student_id = #{studentId}")
    List<RepairOrder> selectRepairOrdersByStudentId(@Param("studentId") String studentId);

    //管理员查询所有报修单
    @Select("SELECT * FROM repair_order")
    List<RepairOrder> selectAllRepairOrders();

    //管理员修改报修状态
    @Update("UPDATE repair_order SET status = #{status} WHERE id = #{orderId}")
    int updateRepairStatus(@Param("orderId") Long orderId, @Param("status") Integer status);

    //管理员删除报修单
    @Delete("DELETE FROM repair_order WHERE id = #{orderId}")
    int deleteRepairOrder(Long orderId);
}