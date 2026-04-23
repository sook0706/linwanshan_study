package com.lostfound.lostfound.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 举报实体类
 * 对应数据库表：report
 * 功能：用户举报失物/捡物信息
 * 3.1.2 必须功能：举报功能
 */
@Data
public class Report {

    // 主键ID，自动递增
    @TableId(type = IdType.AUTO)
    private Long id;
    // 被举报的物品ID（对应lost_item表的id）
    private Long itemId;
    // 举报人ID（对应用户表id）
    private Long userId;
    // 举报理由
    private String reason;
    // 举报状态，0=待审核，1=已处理
    private Integer status;
    // 举报提交时间
    private LocalDateTime createTime;
    // 逻辑删除，0=未删除，1=已删除
    @TableLogic
    private Integer isDelete;
}