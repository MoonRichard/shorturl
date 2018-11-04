package com.ric.shorturl.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class UrlRequest {

    @Size(min = 4, message = "原始连接格式错误.")
    private String url;

    private String tag;
    /**
     * 字符集 0:数字 1:小写字母 2:大写字母 3:大小写字母 others:数字+大小写字母
     */
    private Integer type;

    @Min(value = 4, message = "短链接长度为4~16之间,缺省随机")
    @Max(value = 4, message = "短链接长度为4~16之间,缺省随机")
    private Integer length;

    /**
     * 生成方式：0:MD5方式，1:UUID方式
     */
    @Min(value = 0, message = "配置参数: 0:md5方式，1:uuid方式")
    @Max(value = 1, message = "配置参数: 0:md5方式，1:uuid方式")
    private Integer method;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }


}
