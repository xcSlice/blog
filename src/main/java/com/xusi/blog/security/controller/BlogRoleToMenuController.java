package com.xusi.blog.security.controller;


import com.xusi.blog.common.R;
import com.xusi.blog.security.entity.BlogRoleToMenu;
import com.xusi.blog.security.service.impl.BlogRoleToMenuServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 角色分配菜单表 前端控制器
 * </p>
 *
 * @author xusi
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/api/security/role-to-menu")
@Api(tags = "博客:角色分配菜单",value = "/role-to-menu")
public class BlogRoleToMenuController {
    @Resource
    private BlogRoleToMenuServiceImpl r2mService;

//    为角色分配菜单,只能分配0级菜单
    
    /**
    * @Description: 
    * @Params: [role_id, menuList] -> {long,List<Long>}
    * @return: com.xusi.blog.common.R
    * @Author: xusi
    * @Date: 2020/9/15
    */
    @PostMapping("/set-menu")
    public R setMenu(Long role_id, Long[] menuList){

        List<BlogRoleToMenu> saveList = new ArrayList<>();
//        使用流来新建对象
        saveList = Stream.of(menuList).map(item ->{
            BlogRoleToMenu result = new BlogRoleToMenu();
            result.setRoleId(role_id);
            result.setMenuId(item);
            return result;
        }).collect(Collectors.toList());

        r2mService.saveBatch(saveList);
        return R.ok(200).message("set menu is success");
    }

}

