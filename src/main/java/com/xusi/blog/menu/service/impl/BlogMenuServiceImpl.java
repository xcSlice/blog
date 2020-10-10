package com.xusi.blog.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xusi.blog.common.utils.RedisUtil;
import com.xusi.blog.menu.entity.BlogMenu;
import com.xusi.blog.menu.mapper.BlogMenuMapper;
import com.xusi.blog.menu.service.IBlogMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author xusi
 * @since 2020-09-15
 */
@Service
public class BlogMenuServiceImpl extends ServiceImpl<BlogMenuMapper, BlogMenu> implements IBlogMenuService {
    @Resource
    private RedisUtil redisUtil;


    /**
    * @Description: like its name, exist -> true,no -> false
    * @Param: [id]
    * @return: boolean
    * @Author: xusi
    * @Date: 2020/9/15
    */
    @Override
    public boolean checkIsEntityExistById(Long id) {
        BlogMenu menu = getById(id);
        return menu != null;
    }

    /**
    * @Description: get all menu where pid == 0 ,使用redis缓存
    * @Param: []
    * @return: java.util.List<com.xusi.blog.menu.entity.BlogMenu>
    * @Author: xusi
    * @Date: 2020/9/15
    */
    @Override
    public List<BlogMenu> getAllRoot() {
        String key = "menu:getAllRoot";
        List<BlogMenu> result = (List<BlogMenu>) redisUtil.get(key);
        if (result == null){
            QueryWrapper<BlogMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("menu_pid",0L);
            result = (List<BlogMenu>) redisUtil.setex(key,list(wrapper));
        }
        return result;
    }

    public void saveByPidAndName(Long pid, String name) {
        BlogMenu menu = new BlogMenu();
        menu.setMenuName(name);
        menu.setMenuPid(pid);
        save(menu);
    }
//  封装自带的查询by id, 以实现 redis 缓存功能
    @Override
    public Object selectById(Long id) {
        String key = "menu:selectById:"+id;
        BlogMenu result = (BlogMenu) redisUtil.get(key);
        if (result == null) {
            return redisUtil.setex(key, getById(id));
        } else {
            return result;
        }
    }


}
