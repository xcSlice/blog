package com.xusi.blog.content.service.impl;

import com.xusi.blog.common.utils.RedisUtil;
import com.xusi.blog.content.entity.BlogContent;
import com.xusi.blog.content.mapper.BlogContentMapper;
import com.xusi.blog.content.service.IBlogContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 博客内容表 服务实现类
 * </p>
 *
 * @author xusi
 * @since 2020-09-14
 */
@Service
public class BlogContentServiceImpl extends ServiceImpl<BlogContentMapper, BlogContent> implements IBlogContentService {
    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean entityIsExist(Long id) {
        BlogContent content = getById(id);
        return content != null;
    }


//    使用redis
    @Override
    public List<BlogContent> selectAll() {

        String key =  "content:selectAll" ;
        List<BlogContent> result = (List<BlogContent>) redisUtil.get(key);
        if(result == null){
            return (List<BlogContent>) redisUtil.setex(key,list());
        }
        return result;


    }

//    查询by id
    @Override
    public Object selectById(Long id) {
        String key  = "content:selectById:"+id;
        Object result = redisUtil.get(key);
        if (result != null) {
            return result;
        } else {
            return redisUtil.setex(key,getById(id));
        }
    }
}
