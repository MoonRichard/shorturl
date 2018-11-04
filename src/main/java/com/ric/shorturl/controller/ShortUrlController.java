package com.ric.shorturl.controller;

import com.ric.shorturl.domain.Result;
import com.ric.shorturl.domain.UrlRequest;
import com.ric.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.util.Random;

@Controller
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping("/")
    public ModelAndView getIndex(){
        return new ModelAndView("forward: index.html");
    }

    /**
     * 短链接生成接口
     *
     * @param urlRequest
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/shortUrl")
    public Result generateShortUrl(@Valid UrlRequest urlRequest, HttpServletRequest request){
        // 自定义短链接
        if(!StringUtils.isEmpty(urlRequest.getTag())){
            if (shortUrlService.generateShortUrl(urlRequest.getTag(), urlRequest.getUrl())) {
                return new Result(200, "短链接生成成功", urlRequest.getTag());
            } else {
                return new Result(0, "短链接生成失败，该短链接已被使用", null);
            }
        }
        // 服务器生成短链接
        else {
            if (urlRequest.getType() == null) { urlRequest.setType(-1); }
            if (urlRequest.getLength() == null) {urlRequest.setLength( 4 + new Random().nextInt(12));}
            if (urlRequest.getMethod() == null) { urlRequest.setMethod(0);}
            String tag = shortUrlService.generateShortUrl(
                    urlRequest.getUrl(), urlRequest.getType(), urlRequest.getLength(), urlRequest.getMethod());
            if (!StringUtils.isEmpty(tag)){
                return new Result(200, "短链接生成成功", tag);
            } else {
                return new Result(0, "短链接生成失败，该短链接已被使用", null);
            }
        }
    }

}
