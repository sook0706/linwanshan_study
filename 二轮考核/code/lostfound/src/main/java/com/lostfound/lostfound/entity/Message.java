package com.lostfound.lostfound.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 留言实体
 * 对应题目 3.1.4 留言联系模块
 * 功能：评论、留言、留联系方式、未读消息提醒
 */
@Data
public class Message {
    // 主键自增
    @TableId(type = IdType.AUTO)
    private Long id;

    // 留言属于哪个物品
    private Long itemId;

    // 谁留的言
    private Long fromUserId;

    // 留言发给谁（物品发布者）
    private Long toUserId;

    // 留言内容
    private String content;

    // 留言时留下的电话/微信
    private String contact;

    // 已读状态 0未读 1已读
    private Integer isRead;

    // 留言时间
    private LocalDateTime createTime;

    // 逻辑删除
    @TableLogic
    private Integer isDelete;
}