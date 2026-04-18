package com.lostfound.lostfound.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lostfound.lostfound.entity.Report;
import org.apache.ibatis.annotations.Mapper;

/**
 * 举报信息 Mapper
 * 作用：数据访问层，和数据库交互
 * 继承 BaseMapper 自动拥有增删改查
 */
@Mapper
public interface ReportMapper extends BaseMapper<Report> {
    // MyBatis-Plus 自动实现 CRUD
}
