package com.lostfound.lostfound.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lostfound.lostfound.entity.Message;
import java.util.List;

/**
 * 留言Service接口
 */
public interface MessageService extends IService<Message> {
    // 根据物品ID查所有留言
    List<Message> getByItemId(Long itemId);
}