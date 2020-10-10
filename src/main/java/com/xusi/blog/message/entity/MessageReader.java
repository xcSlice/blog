package com.xusi.blog.message.entity;

import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-09-14 21:10
 **/
public class MessageReader {
    private BlogMessage msg;
    private List<MessageReader> children;

    public MessageReader(){}
    public MessageReader(BlogMessage msg) {
        this.msg = msg;
    }

    public BlogMessage getMsg() {
        return msg;
    }

    public void setMsg(BlogMessage msg) {
        this.msg = msg;
    }

    public List<MessageReader> getChildren() {
        return children;
    }

    public void setChildren(List<MessageReader> children) {
        this.children = children;
    }
}
