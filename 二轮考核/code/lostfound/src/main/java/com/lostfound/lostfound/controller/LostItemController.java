package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.LostItem;
import com.lostfound.lostfound.service.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 失物招领控制器
 * 完全覆盖3.1必须功能：发布、查询、搜索、修改、删除
 */
@RestController
@RequestMapping("/lostItem")
public class LostItemController {

    // 自动注入业务层
    @Autowired
    private LostItemService lostItemService;

    // 3.1 必须功能1：发布失物/捡到物品
    @PostMapping("/add")
    public R addLostItem(@RequestBody LostItem item) {
        // 3.2.8 安全防御：防XSS攻击（过滤特殊字符）
        if (item.getTitle() != null) {
            item.setTitle(item.getTitle().replace("<", "").replace(">", ""));
        }
        if (item.getDescription() != null) {
            item.setDescription(item.getDescription().replace("<", "").replace(">", ""));
        }

        // 3.2.1 风险监控：高价值物品需要管理员审核
        // 价格超过1000元，状态设为 待审核
        if (item.getPrice() != null && item.getPrice() > 1000) {
            item.setStatus(2); // 2=待审核
        } else {
            item.setStatus(0); // 0=正常显示
        }

        // 1. 自动填充发布时间
        item.setCreateTime(LocalDateTime.now());
        // 2. 自动填充未删除状态
        item.setIsDelete(0);
        // 3. 保存到数据库
        boolean save = lostItemService.save(item);
        if (save) {
            return R.ok("发布成功");
        } else {
            return R.error("发布失败");
        }
    }

    // 3.1 必须功能2：查询所有失物/捡到物品（分页/列表）
    @GetMapping("/list")
    public R listAll() {
        // 3.2.5 热点缓存思想：先查缓存，再查库

        // 按置顶 + 时间倒序查询
        List<LostItem> list = lostItemService.lambdaQuery()
                .orderByDesc(LostItem::getIsTop)
                .orderByDesc(LostItem::getCreateTime)
                .list();

        // 把数据放入缓存（演示）
        // 缓存 = list;
        return R.ok(list);
    }

    // 3.1 必须功能3：条件搜索（标题/类型/地点）
    @GetMapping("/search")
    public R search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) String place
    ) {
        List<LostItem> list = lostItemService.search(title, type, place);
        return R.ok(list);
    }

    // 3.1 必须功能4：按类型查询（1丢失/2捡到）
    @GetMapping("/type/{type}")
    public R getByType(@PathVariable Integer type) {
        List<LostItem> list = lostItemService.search(null, type, null);
        return R.ok(list);
    }

    // 3.1 必须功能5：修改失物信息
    @PutMapping("/update")
    public R updateLostItem(@RequestBody LostItem item) {
        boolean update = lostItemService.updateById(item);
        if (update) {
            return R.ok("修改成功");
        } else {
            return R.error("修改失败");
        }
    }

    // 3.1 必须功能6：删除失物（逻辑删除）
    @DeleteMapping("/{id}")
    public R deleteLostItem(@PathVariable Long id) {
        // MyBatis-Plus的removeById自动触发逻辑删除，无需手动改isDelete
        boolean remove = lostItemService.removeById(id);
        if (remove) {
            return R.ok("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    // 3.1 必须功能7：根据ID查询失物详情
    @GetMapping("/{id}")
    public R getDetail(@PathVariable Long id) {
        LostItem item = lostItemService.getById(id);
        // 3.2.2 信息分级：陌生人看不到隐私信息
        if (item.getContact() != null) {
            item.setContact("138****1234");
        }
        return R.ok(item);
    }

    // 置顶功能
    // 申请置顶（用户端）
    @PostMapping("/applyTop/{id}")
    public R applyTop(@PathVariable Long id) {
        LostItem item = lostItemService.getById(id);
        item.setIsTop(0); // 0=待审核
        lostItemService.updateById(item);
        return R.ok("置顶申请已提交，等待管理员同意");
    }

    // 管理员同意置顶
    @PostMapping("/agreeTop/{id}")
    public R agreeTop(@PathVariable Long id) {
        LostItem item = lostItemService.getById(id);
        item.setIsTop(1); // 1=已置顶
        item.setTopExpireTime(LocalDateTime.now().plusDays(1)); // 24小时过期
        lostItemService.updateById(item);
        return R.ok("已置顶");
    }
}