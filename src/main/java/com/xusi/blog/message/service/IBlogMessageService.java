package com.xusi.blog.message.service;

import com.xusi.blog.common.R;
import com.xusi.blog.message.entity.BlogMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 留言表 服务类
 * </p>
 *
 * @author xusi
 * @since 2020-09-14
 */
public interface IBlogMessageService extends IService<BlogMessage> {

    Object selectByContentId(Long id);

    Object selectById(Long id);

    R insert(Long pid, Long user_id, Long content_id, String username, String message_content);

    R updateCustom(Long userId,Long messageId, String updateContent );

    R delete(Long userId, Long messageId);
}
