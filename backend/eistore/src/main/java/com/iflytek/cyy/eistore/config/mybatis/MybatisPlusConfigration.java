package com.iflytek.cyy.eistore.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis Plus 配置
 *
 * @author liuht
 * @create 2019-09-06 15:21
 */
@Configuration
public class MybatisPlusConfigration {

    /**
     * Mybatis plus 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
