package com.lostfound.lostfound.common;

/**
 * 常量类
 * 符合PDF要求：使用常量，不写魔法值
 */
public class Constants {
    // 丢失物品
    public static final Integer TYPE_LOST = 1;
    // 捡到物品
    public static final Integer TYPE_FOUND = 2;
    // 未认领
    public static final Integer STATUS_NOT_FOUND = 0;
    // 已认领
    public static final Integer STATUS_FOUND = 1;
}