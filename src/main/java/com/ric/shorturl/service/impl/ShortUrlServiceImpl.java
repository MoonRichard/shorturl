package com.ric.shorturl.service.impl;

import com.ric.shorturl.dao.ShortUrlDao;
import com.ric.shorturl.entity.ShortUrl;
import com.ric.shorturl.service.ShortUrlOpsService;
import com.ric.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlDao shortUrlDao;
    private final ShortUrlOpsService shortUrlOpsService;

    @Autowired
    public ShortUrlServiceImpl(ShortUrlDao shortUrlDao, ShortUrlOpsService shortUrlOpsService) {
        this.shortUrlDao = shortUrlDao;
        this.shortUrlOpsService = shortUrlOpsService;
    }

    @Override
    public String getUrl(String tag) {
        if (shortUrlOpsService.exist(tag)){
            shortUrlOpsService.increment(tag);
            return shortUrlOpsService.getUrl(tag);
        } else {
            ShortUrl shortUrl = shortUrlDao.findByTag(tag);
            if (shortUrl == null) {
                return null;
            } else {
                shortUrlOpsService.setUrl(shortUrl.getTag(), shortUrl.getUrl());
                shortUrlOpsService.setCount(shortUrl.getTag(), shortUrl.getCount()+1);
                return shortUrl.getUrl();
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean generateShortUrl(String tag, String url) {
        if (shortUrlOpsService.exist(tag)) return false;
        ShortUrl shortUrl = new ShortUrl(tag, url, 0);
        try{
            shortUrlDao.save(shortUrl);
        } catch (Exception e) {
            return false;
        }
        shortUrlOpsService.setUrl(tag, url);
        return true;
    }

    // TODO
    @Override
    public String generateShortUrl(String url, int type, int length, int generator) {
        return null;
    }

    // TODO
    @Override
    public void saveCountToDb() {

    }
}
