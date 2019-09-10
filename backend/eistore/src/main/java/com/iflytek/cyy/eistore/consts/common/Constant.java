package com.iflytek.cyy.eistore.consts.common;

/**
 * 公共常量
 *
 * @author liuht
 */
public interface Constant {

    /**
     * 用户权限列表 SessionKey
     */
    String SESSION_USER_PERMISSION = "user_permissions";

    /**
     * 用户信息 SessionKey
     */
    String SESSION_USER_INFO = "user_info";

    /**
     * 用户密码
     */
    String SESSION_USER_PASSWORD = "user_info_password";

    /**
     * 逻辑删除: 有效
     */
    int STATUS_YES = 1;

    /**
     * 逻辑删除: 已删除
     */
    int STATUS_NO = 0;

}
