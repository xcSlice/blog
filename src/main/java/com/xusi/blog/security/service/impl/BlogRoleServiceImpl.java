package com.xusi.blog.security.service.impl;

import com.xusi.blog.security.entity.BlogRole;
import com.xusi.blog.security.mapper.BlogRoleMapper;
import com.xusi.blog.security.service.IBlogRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author xusi
 * @since 2020-09-13
 */
@Service
public class BlogRoleServiceImpl extends ServiceImpl<BlogRoleMapper, BlogRole> implements IBlogRoleService {

}
