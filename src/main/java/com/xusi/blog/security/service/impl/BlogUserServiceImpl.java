package com.xusi.blog.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xusi.blog.security.entity.BlogUser;
import com.xusi.blog.security.mapper.BlogUserMapper;
import com.xusi.blog.security.service.IBlogUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xusi
 * @since 2020-09-13
 */
@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserMapper, BlogUser> implements IBlogUserService {


    @Override
    public boolean isExistByName(String username) {
        QueryWrapper<BlogUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        BlogUser user = getOne(wrapper);
        System.out.println("user = " + user);
        return user != null;
    }

    @Override
    public boolean isHiddenById(Long id) {
        BlogUser user = getById(id);
//        0 -> false
//        1 -> true
        return user.getIsHidden() != 0;
    }

    @Override
    public BlogUser getOneByName(String username) {
        QueryWrapper<BlogUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        BlogUser user = getOne(wrapper);
        System.out.println("user = " + user);
        return user;
    }

}
