package com.lostfound.lostfound.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.LostItem;
import com.lostfound.lostfound.entity.User;
import com.lostfound.lostfound.mapper.LostItemMapper;
import com.lostfound.lostfound.mapper.UserMapper;
import com.lostfound.lostfound.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员业务实现类
 * 负责：用户管理、失物管理、数据统计、封禁解封等功能
 */
@Service
public class AdminServiceImpl implements AdminService {

    // 用户Mapper，操作用户表
    @Autowired
    private UserMapper userMapper;

    // 失物Mapper，操作失物表
    @Autowired
    private LostItemMapper lostItemMapper;

    /**
     * 获取所有用户列表
     * 用于管理员查看平台所有用户
     */
    @Override
    public R getUserList() {
        List<User> list = userMapper.selectList(null);
        return R.ok(list);
    }

    /**
     * 封禁用户
     * 使用逻辑删除，将 isDelete 设为 1
     * @param userId 要封禁的用户ID
     */
    @Override
    public R banUser(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setIsDelete(1);  // 1=已删除/已封禁
        userMapper.updateById(user);
        return R.ok("封禁成功");
    }

    /**
     * 解封用户
     * 将 isDelete 恢复为 0
     * @param userId 要解封的用户ID
     */
    @Override
    public R unbanUser(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setIsDelete(0);  // 0=正常状态
        userMapper.updateById(user);
        return R.ok("解封成功");
    }

    /**
     * 管理员删除违规失物信息
     * 调用MyBatis-Plus的逻辑删除
     * @param itemId 物品ID
     */
    @Override
    public R deleteItem(Long itemId) {
        lostItemMapper.deleteById(itemId);
        return R.ok("删除违规信息成功");
    }

    /**
     * 平台数据统计
     * 统计：总物品数、已找回数、7天活跃用户数
     */
    @Override
    public R getStatistics() {
        Map<String, Object> map = new HashMap<>();

        // 统计所有物品总数
        long totalItem = lostItemMapper.selectCount(null);
        map.put("totalItem", totalItem);

        // 统计已认领（已找回）物品数量
        LambdaQueryWrapper<LostItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LostItem::getStatus, 1);  // 1=已认领
        long foundItem = lostItemMapper.selectCount(wrapper);
        map.put("foundItem", foundItem);

        // 统计近7天注册的活跃用户
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.ge(User::getCreateTime, LocalDateTime.now().minusDays(7));
        long activeUser = userMapper.selectCount(userWrapper);
        map.put("activeUser", activeUser);

        return R.ok(map);
    }
}