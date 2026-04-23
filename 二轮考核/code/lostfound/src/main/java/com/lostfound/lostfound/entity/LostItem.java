package com.lostfound.lostfound.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LostItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;          // 发布人ID
    private String title;        // 物品名称
    private Integer type;        // 1丢失 2捡到
    private String place;        // 地点
    private LocalDateTime lostTime; // 丢失/捡到时间
    private String description;  // 描述
    private String contact;      // 联系方式
    private String image;        // 图片

    private Integer isTop;       // 0不置顶 1置顶
    private LocalDateTime topExpireTime; // 置顶过期时间

    private Integer status;      // 0未认领 1已认领
    private Double price;       // 物品价格
    private LocalDateTime createTime;

    @TableLogic
    private Integer isDelete;
}