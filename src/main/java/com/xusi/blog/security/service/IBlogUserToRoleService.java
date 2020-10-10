package com.xusi.blog.security.service;

import com.xusi.blog.security.entity.BlogUserToRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户分配角色表 服务类
 * </p>
 *
 * @author xusi
 * @since 2020-09-13
 */
public interface IBlogUserToRoleService extends IService<BlogUserToRole> {
    BlogUserToRole getOneByUserId(Long id);
}
