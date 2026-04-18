package com.lostfound.lostfound.common;

import lombok.Data;

/**
 * 统一返回结果类
 */
@Data
public class R {
    // 响应码：200成功，500失败
    private Integer code;
    // 响应消息
    private String msg;
    // 响应数据
    private Object data;

    // 成功响应（带数据）
    public static R ok(Object data) {
        R r = new R();
        r.setCode(200);
        r.setMsg("成功");
        r.setData(data);
        return r;
    }

    // 成功响应（不带数据，只带消息）
    public static R ok(String msg) {
        R r = new R();
        r.setCode(200);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }

    // 失败响应（带消息）
    public static R error(String msg) {
        R r = new R();
        r.setCode(500);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }
}