package com.xusi.blog.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xusi.blog.common.R;
import com.xusi.blog.common.utils.RedisUtil;
import com.xusi.blog.message.entity.BlogMessage;
import com.xusi.blog.message.entity.MessageReader;
import com.xusi.blog.message.mapper.BlogMessageMapper;
import com.xusi.blog.message.service.IBlogMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xusi.blog.security.service.IBlogUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 留言表 服务实现类
 * </p>
 *
 * @author xusi
 * @since 2020-09-14
 */
@Service
public class BlogMessageServiceImpl extends ServiceImpl<BlogMessageMapper, BlogMessage> implements IBlogMessageService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private IBlogUserService userService;

    @Override
    public Object selectByContentId(Long id) {
        String key = "message:selectByContentId:"+id;
        List<MessageReader> result = (List<MessageReader>) redisUtil.get(key);
        if (result == null) {
            //        wrapper content id
            QueryWrapper<BlogMessage> wrapper = new QueryWrapper<>();
            wrapper.eq("content_id",id);
            List<BlogMessage> messageList = list(wrapper);
//        blogmessage to messageReader
            List<MessageReader> rootList = new ArrayList<>();
            messageList.forEach(msg -> {
                rootList.add(new MessageReader(msg));
            });

//        递归封装评论
            List<MessageReader> finalNode = new ArrayList<>();
//        遍历messageList获取根评论
            rootList.stream()
                    .filter(reader -> reader.getMsg().getParentId() == 0)
                    .forEach(reader -> {
                        reader.setChildren(selectChildren(reader.getMsg().getId(),rootList));
                        finalNode.add(reader);
                    });
            // 设置缓存
            return redisUtil.setex(key ,finalNode);

        } else {
            return result;
        }

    }

    @Override
    public Object selectById(Long id) {
        String key = "message:selectById:" +id;
        BlogMessage result = (BlogMessage) redisUtil.get(key);
        if (result == null) {
            return redisUtil.setex(key,getById(id));
        } else {
            return result;
        }
    }

    @Override
    public R insert(Long pid, Long user_id, Long content_id, String username, String message_content) {
        //      用户是否已被拉黑
        if(userService.isHiddenById(user_id)){
            return R.error(500).message("user is hidden");
        }
//        wrapper message
        BlogMessage message = new BlogMessage();
        message.setContent(message_content);
        message.setUserId(user_id);
        message.setContentId(content_id);
        message.setUserName(username);
        message.setParentId(pid);
//        save to database
        save(message);
        return R.ok(200).message("save success");
    }

    @Override
    public R updateCustom(Long userId, Long messageId, String updateContent) {
        //      用户是否已被拉黑
        if (userService.isHiddenById(userId)){
            return R.error(500).message("用户或者评论对象有误");
        }
        BlogMessage message = getById(messageId);
        message.setContent(updateContent);
        updateById(message);
        return R.ok(200).message("update is success");
    }

    //    删除评论, 实际为更新评论, 将内容设为 评论已被作者删除 作者设为null
    @Override
    public R delete(Long userId, Long messageId) {
        //      用户是否已被拉黑
        if (userService.isHiddenById(userId)){
            return R.error(500).message("用户或者评论对象有误");
        }
        BlogMessage message = getById(messageId);
        message.setId(messageId);
        message.setContent("评论已被作者删除!");
        message.setUserId(0L);
        updateById(message);
        return R.ok(200).message("delete is success");
    }


    //    根据 pid id 递归查询
    private List<MessageReader> selectChildren(Long pid,List<MessageReader> rootList){
        List<MessageReader> children = new ArrayList<>();
//        递归设置 children
        rootList.stream()
                .filter(reader -> reader.getMsg().getParentId() == pid)
                .forEach(reader -> {
                    reader.setChildren(selectChildren(reader.getMsg().getId(),rootList));
                    children.add(reader);
                });

        return children;
    }
}
