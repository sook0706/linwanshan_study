package com.lostfound.lostfound.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lostfound.lostfound.entity.LostItem;
import com.lostfound.lostfound.mapper.LostItemMapper;
import com.lostfound.lostfound.service.LostItemService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 失物招领业务实现类
 */
@Service
public class LostItemServiceImpl extends ServiceImpl<LostItemMapper, LostItem> implements LostItemService {

    /**
     * 条件搜索：支持标题模糊查询、类型精准查询、地点精准查询
     * 参数顺序：title(字符串), type(整数), place(字符串)
     */
    @Override
    public List<LostItem> search(String title, Integer type, String place) {
        // 构建查询条件
        LambdaQueryWrapper<LostItem> wrapper = new LambdaQueryWrapper<>();

        // 动态拼接条件（参数不为空才生效）
        if (title != null && !title.isEmpty()) {
            wrapper.like(LostItem::getTitle, title); // 标题模糊匹配
        }
        if (type != null) {
            wrapper.eq(LostItem::getType, type); // 类型精准匹配（1丢失/2捡到）
        }
        if (place != null && !place.isEmpty()) {
            wrapper.eq(LostItem::getPlace, place); // 地点精准匹配
        }

        // 按发布时间倒序（最新的在最前面）
        wrapper.orderByDesc(LostItem::getCreateTime);

        // 执行查询，返回结果
        return list(wrapper);
    }
}