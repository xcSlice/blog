package com.xusi.blog.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: IntelliJ IDEA
 * @description: 统一数据返回值
 * @author: xusi
 * @create:2020-09-05 21:27
 **/
public class R {
    private Integer code;
    private String message;
    private Map<String,Object> data = new HashMap<>();

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    //    返回成功的数据
    public static R ok(int code){
        R r = new R();
        r.setCode(code);
        return r;
    }

//    返回失败的数据
    public static R error(int code){
        R r = new R();
        r.setCode(code);
        return r;
    }

//    设置数据
    public R data(Map<String, Object> map){

        this.setData(map);
        return this;
    }

//    重载设置数据

    public R data(String key,Object value ){

        this.data.put(key, value);
        return this;
    }

    public  R message(String msg){
        this.setMessage(msg);
        return this;
    }

}
