package com.imantou.common.captcha.cache;

import java.util.concurrent.ConcurrentHashMap;

public class BufferedImageCacheManager extends ConcurrentHashMap<Object, Object> implements CaptchaCacheManager{

    @Override
    public Object getCache(Object key) {
        return get(key);
    }

    @Override
    public void removeCache(Object key) {
        remove(key);
    }

    @Override
    public Object setCache(Object key, Object value) {
        return put(key,value);
    }
}
