package com.xusi.blog.message.controller;



import com.xusi.blog.common.R;
import com.xusi.blog.message.service.IBlogMessageService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 留言表 前端控制器
 * </p>
 *
 * @author xusi
 * @since 2020-09-14
 */
@RestController
@Api(tags = "博客:评论管理", value = "/message")
@RequestMapping("/message")
public class BlogMessageController {
    @Resource
    private IBlogMessageService messageService;

//    根据博文 id 获取博文评论
    @GetMapping("/content/{id}")
    public R getAllById(@PathVariable Long id){
        return R.ok(200).data("data",messageService.selectByContentId(id));
    }


//    根据评论id获取评论, 少用
    @GetMapping("/{id}")
    public R getOne(@PathVariable Long id){
        return R.ok(200).data("data",messageService.selectById(id));
    }

//    添加评论
    @PostMapping("/insert")
    public R insert(Long pid, Long user_id,Long content_id, String username, String message_content){
        return messageService.insert(pid,user_id,content_id,username,message_content);
    }
//    更新评论
    @PutMapping
    public R update(Long userId,Long messageId, String updateContent ){

        return messageService.updateCustom(userId, messageId, updateContent);
    }

//    删除评论, 实际为更新评论, 将内容设为 评论已被作者删除 作者设为null
    @DeleteMapping
    public R delete(Long userId,Long messageId){
        return messageService.delete(userId,messageId);
    }

}

