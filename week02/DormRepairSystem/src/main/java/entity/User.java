package com.dorm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //自动生成get/set/toString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id; //主键
    private String username; //用户名（登录用）
    private String password; //密码
    private String role; //角色：student（学生）/admin（管理员）
    private String dormNum; //学生绑定的宿舍号（管理员为空）
    private String phone; //联系方式（可选）
}