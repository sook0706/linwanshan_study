package com.lostfound.lostfound.controller;

import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.entity.LostItem;
import com.lostfound.lostfound.service.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * AI辅助模块控制器
 * 功能：AI自动生成物品描述（3.1.5要求）
 */
@RestController
@RequestMapping("/ai")
public class AIController {

    // 自动注入失物服务，用来更新数据库
    @Autowired
    private LostItemService lostItemService;

    /**
     * AI自动生成物品描述
     * 按物品标题自动生成规范描述，并存入数据库
     */
    @PostMapping("/generateDesc")
    public R generateDesc(@RequestBody LostItem item) {
        // 先拿到物品标题，判断物品类型
        String title = item.getTitle();
        String desc = "";

        // 按物品类型生成对应描述（完全按需求来）
        if (title.contains("校园卡")) {
            desc = "该物品为校园卡，用于身份认证与消费，请丢失者尽快联系领取。";
        } else if (title.contains("耳机")) {
            desc = "该物品为耳机，属于常用电子设备，请失主及时确认领取。";
        } else if (title.contains("钥匙")) {
            desc = "该物品为钥匙，涉及宿舍安全，失主请尽快核对信息领取。";
        } else {
            desc = "该物品为日常用品，请失主及时联系发布者。";
        }

        // 把生成的描述更新到数据库里
        LostItem lostItem = lostItemService.getById(item.getId());
        if (lostItem == null) {
            return R.error("物品不存在");
        }
        lostItem.setDescription(desc);
        lostItemService.updateById(lostItem);

        // 返回成功，带生成的描述
        return R.ok("AI生成描述成功，内容为：" + desc);
    }
}