package com.lostfound.lostfound.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @TableId(type = IdType.AUTO) // 主键ID，设置为数据库自增
    private Long id;             // 对应user表的id，存用户唯一ID
    private String username;     // 对应user表的username，存用户名
    private String password;     // 对应user表的password，存加密后的密码，绝对不存明文
    private String phone;        // 对应user表的phone，存手机号，作为登录账号
    private LocalDateTime createTime; // 对应user表的create_time，自动存注册时间
    private String email;        // 邮箱
    @TableField(exist = false)
    private String avatar;       // 头像
    // @TableLogic：逻辑删除注解，对应user表的is_delete字段
    // 0=未删除，1=已删除
    @TableLogic
    private Integer isDelete;

    // 0=普通用户 1=管理员
    private Integer role;
    // 0=正常 1=封禁（封禁后无法登录）
    private Integer status = 0;
}