package com.liangyuelong.blog.utils;

/**
 * 公共操作工具类
 *
 * @author yuelong.liang
 */
public class CommUtils {

    /**
     * 判断传入的 Integer 参数，如果为空返回 0
     * 否则返回 int 类型的 num
     *
     * @param num num
     * @return int
     */
    public static int null2Int(Integer num) {
        return num == null ? 0 : num;
    }

}
