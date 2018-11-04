package com.ric.shorturl.service.impl;

import com.ric.shorturl.service.ShortUrlOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShortUrlOpsServiceImpl implements ShortUrlOpsService {

    private final StringRedisTemplate redisTemplate;

    private final String short_url = "shortUrl";
    private final String short_url_count = "shortUrlCount";

    @Autowired
    public ShortUrlOpsServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void putUrls(Map<String, String> map) {
        redisTemplate.opsForHash().putAll(short_url, map);
    }

    @Override
    public void putCount(Map<String, String> map) {
        redisTemplate.opsForHash().putAll(short_url_count, map);
    }

    @Override
    public String getUrl(String tag) {
        return (String)redisTemplate.opsForHash().get(short_url, tag);
    }

    @Override
    public void setUrl(String tag, String url) {
        redisTemplate.opsForHash().put(short_url, tag, url);
    }

    @Override
    public boolean exist(String tag) {
        return redisTemplate.opsForHash().hasKey(short_url, tag);
    }

    @Override
    public void setCount(String tag, int count) {
        redisTemplate.opsForHash().put(short_url_count, tag, String.valueOf(count));
    }

    @Override
    public long increment(String tag) {
        return redisTemplate.opsForHash().increment(short_url_count, tag, 1);
    }

    @Override
    public Map<Object, Object> getAllCount() {
        return redisTemplate.opsForHash().entries(short_url_count);
    }

    @Override
    public void clearCount() {
        redisTemplate.delete(short_url_count);
    }

    @Override
    public void clearUrl() {
        redisTemplate.delete(short_url);
    }
}
