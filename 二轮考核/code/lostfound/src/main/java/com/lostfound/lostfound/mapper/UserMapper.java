package com.lostfound.lostfound.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lostfound.lostfound.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 * 作用：负责和数据库交互，操作用户表的增删改查
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}