package com.imantou.common.captcha.cache;

public interface CaptchaCacheManager {
    /**
     * 获取缓存数据
     *
     * @return 缓存数据
     */
    Object getCache(Object key);

    /**
     * 移除缓存数据
     *
     * @param key 要清除的内容
     */
    void removeCache(Object key);

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     * @return 值
     */
    Object setCache(Object key, Object value);
}
