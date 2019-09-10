package com.iflytek.cyy.eistore.config.dozer;

import java.util.List;
import java.util.Set;

/**
 * Dozer 转换接口
 *
 * @author liuht
 * 2018/10/30 9:39
 */
public interface DozerConverter {
    /**
     * 单个对象的深度复制及类型转换，vo/domain , po
     *
     * @param s   数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> T convert(S s, Class<T> clz);

    /**
     * list深度复制
     *
     * @param s   数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> List<T> convert(List<S> s, Class<T> clz);

    /**
     * set深度复制
     *
     * @param s   数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> Set<T> convert(Set<S> s, Class<T> clz);

    /**
     * 数组深度复制
     *
     * @param s   数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> T[] convert(S[] s, Class<T> clz);
}
