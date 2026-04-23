package com.lostfound.lostfound.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lostfound.lostfound.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * 留言Mapper
 * 负责留言表的数据库操作
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}