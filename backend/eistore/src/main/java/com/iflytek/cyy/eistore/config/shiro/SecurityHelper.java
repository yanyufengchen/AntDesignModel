package com.iflytek.cyy.eistore.config.shiro;

import com.iflytek.cyy.eistore.consts.common.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * shiro 安全框架辅助类
 *
 * @author liuht
 * 2018/10/21 15:04
 */
public final class SecurityHelper {

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static SessionUser getLoginUser() {
        final Session session = SecurityUtils.getSubject().getSession();
        if (session.getAttribute(Constant.SESSION_USER_INFO) != null) {
            return (SessionUser) session.getAttribute(Constant.SESSION_USER_INFO);
        }
        return new SessionUser();
    }

}
