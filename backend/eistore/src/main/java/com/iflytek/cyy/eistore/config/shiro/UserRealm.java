package com.iflytek.cyy.eistore.config.shiro;

import com.iflytek.cyy.eistore.consts.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


/**
 * 自定义shiro realm
 *
 * @author liuht
 * 2018/10/19 14:49
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    /**
     * 权限注入(未使用)
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 用户认证
     *
     * @param authcToken 用户token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authcToken) throws
            AuthenticationException {
        // 域账号
        final String account = (String) authcToken.getPrincipal();
        // 密码
        final String password = new String((char[]) authcToken.getCredentials());
        final SessionUser sessionUser = new SessionUser(account);
        SecurityUtils.getSubject().getSession().setAttribute(Constant.SESSION_USER_INFO, sessionUser);
        return new SimpleAuthenticationInfo(account, password, getName());
    }
}
