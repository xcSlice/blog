package com.xusi.blog.security.service;

import com.xusi.blog.security.entity.BlogUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xusi
 * @since 2020-09-13
 */
public interface IBlogUserService extends IService<BlogUser> {
    boolean isExistByName(String username);
    boolean isHiddenById(Long id);

    BlogUser getOneByName(String username);
}
