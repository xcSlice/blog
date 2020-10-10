package com.xusi.blog.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xusi.blog.security.entity.BlogUser;
import com.xusi.blog.security.entity.BlogUserToRole;
import com.xusi.blog.security.mapper.BlogUserToRoleMapper;
import com.xusi.blog.security.service.IBlogUserToRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户分配角色表 服务实现类
 * </p>
 *
 * @author xusi
 * @since 2020-09-13
 */
@Service
public class BlogUserToRoleServiceImpl extends ServiceImpl<BlogUserToRoleMapper, BlogUserToRole> implements IBlogUserToRoleService {

    /**
    * @Description: get by id
    * @Param: [id]
    * @return: com.xusi.blog.security.entity.BlogUserToRole
    * @Author: xusi
    * @Date: 2020/9/15
    */
    @Override
    public BlogUserToRole getOneByUserId(Long id) {
        QueryWrapper<BlogUserToRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        BlogUserToRole u2r = getOne(wrapper);
//        如果查询到返回, 为空则new
        if (u2r != null) {
            return u2r;
        } else {
            return new BlogUserToRole();
        }
    }
}
