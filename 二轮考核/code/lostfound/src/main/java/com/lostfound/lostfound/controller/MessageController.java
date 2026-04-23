package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.Message;
import com.lostfound.lostfound.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 留言控制器
 * 对应题目 3.1.4 留言联系模块
 * 功能：发布留言、查看物品留言、查看未读消息、标记已读
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 发布留言（可附带联系方式）
     */
    @PostMapping("/add")
    public R add(@RequestBody Message message, HttpServletRequest request) {
        // 从Token获取当前登录用户ID（修复数据库报错）
        Long userId = (Long) request.getAttribute("userId");
        message.setFromUserId(userId);

        // 自动填充时间
        message.setCreateTime(LocalDateTime.now());
        // 默认未读
        message.setIsRead(0);
        messageService.save(message);
        return R.ok("留言成功，对方会收到消息提醒");
    }

    /**
     * 根据物品ID查看所有留言
     */
    @GetMapping("/item/{itemId}")
    public R getByItemId(@PathVariable Long itemId) {
        List<Message> list = messageService.getByItemId(itemId);
        return R.ok(list);
    }

    /**
     * 查看我的未读消息
     */
    @GetMapping("/unread/{userId}")
    public R getUnread(@PathVariable Long userId) {
        List<Message> list = messageService.lambdaQuery()
                .eq(Message::getToUserId, userId)
                .eq(Message::getIsRead, 0)
                .list();
        return R.ok(list);
    }

    /**
     * 标记消息为已读
     */
    @PostMapping("/read/{id}")
    public R setRead(@PathVariable Long id) {
        Message msg = messageService.getById(id);
        msg.setIsRead(1);
        messageService.updateById(msg);
        return R.ok("已标记为已读");
    }
}