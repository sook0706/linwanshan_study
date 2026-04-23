package com.lostfound.lostfound.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lostfound.lostfound.entity.LostItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 失物Mapper接口
 * 作用：负责和数据库交互，操作失物表的增删改查
 */
@Mapper
public interface LostItemMapper extends BaseMapper<LostItem> {

}