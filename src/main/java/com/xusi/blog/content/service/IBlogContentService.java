package com.xusi.blog.content.service;

import com.xusi.blog.content.entity.BlogContent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 博客内容表 服务类
 * </p>
 *
 * @author xusi
 * @since 2020-09-14
 */
public interface IBlogContentService extends IService<BlogContent> {
    boolean entityIsExist(Long id);

    List<BlogContent> selectAll();

    Object selectById(Long id);
}
