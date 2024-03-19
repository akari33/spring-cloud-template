package com.temp.common.constant;

/**
 * redis constant
 *
 * @Author Administrator
 * @Date 2023/8/4 15:11
 **/
public interface RedisConstants {

    /**
     * 黑名单TOKEN Key前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";

    /**
     * JWT 密钥对(包含公钥和私钥)
     */
    String JWK_SET_KEY = "jwk_set";

    /**
     * 登录短信验证码key前缀
     */
    String LOGIN_SMS_CODE_PREFIX = "sms_code:login";

    /**
     * 注册短信验证码key前缀
     */
    String REGISTER_SMS_CODE_PREFIX = "sms_code:register";

    /**
     * 角色和权限缓存前缀
     */
    String ROLE_PERMS_PREFIX = "role_perms:";

}
