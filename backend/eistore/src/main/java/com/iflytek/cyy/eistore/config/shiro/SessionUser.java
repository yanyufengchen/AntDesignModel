package com.iflytek.cyy.eistore.config.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户扩展信息
 *
 * @author liuht
 * 2018/10/19 17:20
 */
@Data
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 8745973602613353846L;

    public SessionUser() {

    }

    public SessionUser(String account) {
        this.account = account;
    }

    /**
     * 用户账号 唯一标识
     */
    private String account;
}
