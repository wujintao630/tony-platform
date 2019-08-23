package com.tonytaotao.service.common.handler;

import java.util.UUID;

/**
 * 全局ID生成器
 * @author wujintao
 */
public class GlobalIDHandler {

    public static ThreadLocal<String> ids = new ThreadLocal<>();

    public static void setId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        ids.set(uuid);
    }

    public static String getId() {
        return ids.get();
    }

}