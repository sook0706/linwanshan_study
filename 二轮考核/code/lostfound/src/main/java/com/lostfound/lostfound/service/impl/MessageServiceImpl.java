package com.lostfound.lostfound.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lostfound.lostfound.entity.Message;
import com.lostfound.lostfound.mapper.MessageMapper;
import com.lostfound.lostfound.service.MessageService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 留言业务实现
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    /**
     * 根据物品ID查询所有留言
     * 按时间正序，最早的在前
     */
    @Override
    public List<Message> getByItemId(Long itemId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getItemId, itemId);
        wrapper.orderByAsc(Message::getCreateTime);
        return list(wrapper);
    }
}