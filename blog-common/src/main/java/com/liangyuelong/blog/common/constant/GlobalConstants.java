package com.liangyuelong.blog.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 全局常用变量 </p>
 *
 * @author : zhengqing
 * @date : 2019/10/12 14:47
 */
public class GlobalConstants {

    /**
     * 接口url
     */
    public static Map<String, String> URL_MAPPING_MAP = new HashMap<>();

    /**
     * 获取项目根目录
     */
    public static String PROJECT_ROOT_DIRECTORY = System.getProperty("user.dir");

    /**
     * 密码加密相关
     */
    public static String SALT = "npc2048_blog";
    public static final int HASH_ITERATIONS = 1;

    /**
     * 请求头 - token
     */
    public static final String REQUEST_HEADER = "Authorization";

    /**
     * 请求头类型：
     * application/x-www-form-urlencoded ： form表单格式
     * application/json ： json格式
     */
    public static final String REQUEST_HEADERS_CONTENT_TYPE = "application/json";

    /**
     * 登录者角色
     */
    public static final String ROLE_LOGIN = "role_login";

    /**
     * 验证码
     */
    public static final String VERIFY_CODE = "user:verifyCode:";

    /**
     * 登录次数
     */
    public static final String LOGIN_NUM = "user:loginNum:";

}
