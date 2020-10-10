package com.xusi.blog.security.controller;


import com.xusi.blog.common.R;
import com.xusi.blog.security.entity.BlogUserToRole;
import com.xusi.blog.security.service.impl.BlogUserToRoleServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户分配角色表 前端控制器
 * </p>
 *
 * @author xusi
 * @since 2020-09-13
 */
@RestController
@RequestMapping("api/security/user-to-role")
@Api(tags = "博客:用户分配角色",value = "/user-to-role")
public class BlogUserToRoleController {
    @Resource
    private BlogUserToRoleServiceImpl u2rService;

    @PostMapping("/set-role")
    public R setRole(String role,Long userId){
//        根据用户 id 查询权限
        BlogUserToRole u2r = u2rService.getOneByUserId(userId);
        boolean paraIsTrue = true;
//        根据 role 不同 设置不同的 role id, 默认为2
        switch (role.toUpperCase()){
            case "SUPER": u2r.setRoleId(0L);break;
            case "ADMIN": u2r.setRoleId(1L);break;
            case "USER": u2r.setRoleId(2L);break;
            default:u2r.setRoleId(2L);paraIsTrue = false;
        }

//        通过判断 id 存在确定是否存在数据库中, 存在则更新, 不存在则新增
        System.out.println(" = " + u2r.getRoleId());
        u2r.setGmtModified(null);
        u2rService.saveOrUpdate(u2r);
        R r = R.ok(200);
        if (paraIsTrue) {
            return r.message("set role is success");
        } else {
            return r.message("参数有误,默认设置为USER");
        }


    }
}

