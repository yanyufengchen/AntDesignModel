package com.iflytek.cyy.eistore.config.shiro;

import com.iflytek.cyy.eistore.consts.common.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置类
 *
 * @author liuht
 * 2018/10/19 14:44
 */
@Configuration
@Slf4j
public class ShiroConfiguration {

    @Value("${server.servlet.path:''}")
    private String contextPath;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Shiro的Web过滤器Factory 命名:shiroFilter
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //把securityManager加到环境变量中
        SecurityUtils.setSecurityManager(securityManager);
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 自定义拦截器
        Map<String, Filter> filters = new HashMap<>(1);
        // 定义shiro过滤链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put(contextPath + "/", "anon");
        filterChainDefinitionMap.put(contextPath + "/static/**", "anon");
        filterChainDefinitionMap.put(contextPath + "/api/v1/authc/**", "anon");
        filterChainDefinitionMap.put(contextPath + "/error", "anon");
        filterChainDefinitionMap.put(contextPath + "/swagger-ui.html", "anon");
        filterChainDefinitionMap.put(contextPath + "/webjars/springfox-swagger-ui/**", "anon");
        filterChainDefinitionMap.put(contextPath + "/swagger-resources/**", "anon");
        filterChainDefinitionMap.put(contextPath + "/v2/api-docs", "anon");
        filterChainDefinitionMap.put(contextPath + "/**", "authc");
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setLoginUrl(contextPath + "/api/v1/authc/nologin");
        shiroFilterFactoryBean.setUnauthorizedUrl(contextPath + "/api/v1/authc/unauthorized");
        return shiroFilterFactoryBean;
    }

    /**
     * shiro SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(new RedisCacheManager(CacheConstant.USER_SESSIONS, redisTemplate, CacheConstant.DEFAULT_TIMEOUT));
        securityManager.setRealm(userRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 自定义 Shiro Realm
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * session 管理对象
     *
     * @return DefaultWebSessionManager
     */
    private DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(CacheConstant.DEFAULT_TIMEOUT * 1000);
        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return sessionManager;
    }
}
