package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.LostItem;
import com.lostfound.lostfound.service.LostItemService;
import com.lostfound.lostfound.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    private JwtUtil jwtUtil;

    // 3.1 必须功能1：发布失物/捡到物品
    @PostMapping("/add")
    public R addLostItem(@RequestBody LostItem item, HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(token);
            item.setUserId(userId);

            if (item.getTitle() != null) {
                item.setTitle(item.getTitle().replace("<", "").replace(">", ""));
            }
            if (item.getDescription() != null) {
                item.setDescription(item.getDescription().replace("<", "").replace(">", ""));
            }

            if (item.getPrice() != null && item.getPrice() > 1000) {
                item.setStatus(2);
            } else {
                item.setStatus(0);
            }

            item.setCreateTime(LocalDateTime.now());
            item.setIsDelete(0);
            lostItemService.save(item);
            return R.ok("发布成功");
        } catch (Exception e) {
            return R.error("请先登录");
        }
    }

    // 3.1 必须功能2：查询所有失物/捡到物品（分页/列表）
    @GetMapping("/list")
    public R listAll() {
        // 按置顶 + 时间倒序查询
        List<LostItem> list = lostItemService.lambdaQuery()
                .orderByDesc(LostItem::getIsTop)
                .orderByDesc(LostItem::getCreateTime)
                .list();

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
    @PostMapping("/update")
    public R updateLostItem(@RequestBody LostItem item, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return R.error("请先登录");
        }
        token = token.replace("Bearer ", "");
        Long userId;
        try {
            userId = jwtUtil.getUserIdFromToken(token);
        } catch (Exception e) {
            return R.error("登录已过期，请重新登录");
        }

        LostItem old = lostItemService.getById(item.getId());
        if (old == null) {
            return R.error("信息不存在");
        }

        // 这里加了防御
        if (old.getUserId() == null) {
            return R.error("不能修改旧数据，请发布新数据测试");
        }

        if (!old.getUserId().equals(userId)) {
            return R.error("只能修改自己发布的信息");
        }

        boolean update = lostItemService.updateById(item);
        return update ? R.ok("修改成功") : R.error("修改失败");
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
        if (id == null) return R.error("物品不存在");
        LostItem item = lostItemService.getById(id);
        if (item == null) return R.error("物品不存在");
        item.setIsTop(0);
        lostItemService.updateById(item);
        return R.ok("置顶申请已提交");
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