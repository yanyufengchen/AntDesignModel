package com.iflytek.cyy.eistore.controller.system;

import com.iflytek.cyy.eistore.config.shiro.SecurityHelper;
import com.iflytek.cyy.eistore.config.shiro.SessionUser;
import com.iflytek.lingxi.common.util.constant.DefaultErrorCode;
import com.iflytek.lingxi.common.util.exception.AuthcErrorException;
import com.iflytek.lingxi.common.util.web.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录
 *
 * @author liuht
 * 2018/10/19 17:45
 */
@RestController
@RequestMapping("/api/v1/authc")
public class LoginController {

    /**
     * 登录
     *
     * @param params  参数map
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Result> authLogin(@RequestBody Map params) {
        final String accountKey = "account";
        final String passwordKey = "password";
        if (!params.containsKey(accountKey) || !params.containsKey(passwordKey)) {
            throw new AuthcErrorException("登录验证失败, 登录参数不全");
        }
        final String account = params.get(accountKey).toString();
        final String password = params.get(passwordKey).toString();
        final UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        SecurityUtils.getSubject().login(token);
        return ResponseEntity.ok(Result.success(account));
    }

    /**
     * 查询当前登录用户的信息
     *
     * @return 登录用户
     */
    @GetMapping("/userinfo")
    public ResponseEntity<Result> getUserInfo() {
        final SessionUser loginUser = SecurityHelper.getLoginUser();
        if (loginUser == null) {
            Result.failure(DefaultErrorCode.NO_LOGIN);
        }
        return ResponseEntity.ok(Result.success(loginUser));
    }

    /**
     * 未登录
     *
     * @return 未登录提示信息
     */
    @GetMapping("/nologin")
    public ResponseEntity<Result> nologin() {
        return ResponseEntity.ok(Result.failure(DefaultErrorCode.NO_LOGIN));
    }

    /**
     * 没有权限访问地址
     *
     * @return 没有权限提示信息
     */
    @GetMapping("/unauthorized")
    public ResponseEntity<Result> unauthorized() {
        return ResponseEntity.ok(Result.failure(DefaultErrorCode.ACCESS_DENIED));
    }

    /**
     * 登出
     *
     * @return 退出登录结果
     */
    @PostMapping("/logout")
    public ResponseEntity<Result> logout() {
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok(Result.success(null));
    }
}
