package com.xusi.blog.common.exception;

/**
 * @program: IntelliJ IDEA
 * @description: token
 * @author: xusi
 * @create:2020-10-03 15:13
 **/
public class TokenException extends RuntimeException{
    public TokenException(){}

    public TokenException(Throwable cause) {
        super(cause);
    }
    public TokenException(String message){

    }
}
