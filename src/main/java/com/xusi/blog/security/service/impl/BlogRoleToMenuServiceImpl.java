package com.xusi.blog.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xusi.blog.common.utils.RedisUtil;
import com.xusi.blog.menu.entity.BlogMenu;
import com.xusi.blog.menu.entity.MenuReader;
import com.xusi.blog.menu.service.impl.BlogMenuServiceImpl;
import com.xusi.blog.security.entity.BlogRoleToMenu;
import com.xusi.blog.security.mapper.BlogRoleToMenuMapper;
import com.xusi.blog.security.service.IBlogRoleToMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色分配菜单表 服务实现类
 * </p>
 *
 * @author xusi
 * @since 2020-09-15
 */
@Service
public class BlogRoleToMenuServiceImpl extends ServiceImpl<BlogRoleToMenuMapper, BlogRoleToMenu> implements IBlogRoleToMenuService {
    @Resource
    private BlogMenuServiceImpl menuService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<MenuReader> getByRoleId(Long id) {

        String key = "menu:getByRoleId:"+id;
        List<MenuReader> result = (List<MenuReader>) redisUtil.get(key);
        if (result != null) {
            return result;
        }
        List<Long> menuPid = new ArrayList<>();
        QueryWrapper<BlogRoleToMenu> wrapper = new QueryWrapper<>();

        wrapper.eq("role_id", id);
        List<BlogRoleToMenu> b2mList = list(wrapper);
//        将获取list的 menu id 传入 resultList
        b2mList.forEach(item -> {
            menuPid.add(item.getMenuId());
        });


        //        获取所有菜单
        List<BlogMenu> menuList = menuService.list();
        //        获取所有 零级的菜单入口
        List<BlogMenu> entryMenuList = menuService.listByIds(menuPid);
//        创建菜单查询类
        List<MenuReader> finalNode = new ArrayList<>();
//        遍历入口菜单 to final
        entryMenuList.forEach(menu -> {
            finalNode.add(new MenuReader(menu));
        });
//        为finalNode 设置 children
        finalNode.forEach(reader -> {
            reader.setChildren(selectChildren(reader.getMenu().getId(), menuList));
        });

        // redis cache
        redisUtil.setex(key,finalNode);
        return finalNode;
    }


    //    递归查询子
    private List<MenuReader> selectChildren(Long pid, List<BlogMenu> menuList){
        List<MenuReader> childrenList = new ArrayList<>();

//        filter pi == pid, 然后查询他们各自的 children ,添加到children中,最后返回
        menuList.stream().filter(menu -> menu.getMenuPid().equals(pid))
                .forEach(menu -> {
                    MenuReader reader = new MenuReader(menu);
                    reader.setChildren(selectChildren(menu.getId(),menuList));
                    childrenList.add(reader);

                });

        return childrenList;
    }

}
