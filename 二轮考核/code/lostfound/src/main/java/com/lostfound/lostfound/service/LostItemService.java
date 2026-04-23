package com.lostfound.lostfound.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lostfound.lostfound.entity.LostItem;
import java.util.List;

/**
 * 失物招领业务接口
 */
public interface LostItemService extends IService<LostItem> {
    /**
     * 条件搜索失物（标题/类型/地点）
     */
    List<LostItem> search(String title, Integer type, String place);
}