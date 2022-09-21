package com.haoyang.commons.utils;

import java.util.UUID;

/**
 * 获取UUID工具类
 * @author haoyang
 */
public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
