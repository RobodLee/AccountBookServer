package com.robod.utils;

import java.util.UUID;

/**
 * UUID的工具类，用于生成UUID字符串
 * @author 李迪
 * @date 2020/3/7 15:41
 */
public class UuidUtil {

    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
