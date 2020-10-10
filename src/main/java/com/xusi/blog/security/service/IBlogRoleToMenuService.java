package com.xusi.blog.security.service;

import com.xusi.blog.menu.entity.MenuReader;
import com.xusi.blog.security.entity.BlogRoleToMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色分配菜单表 服务类
 * </p>
 *
 * @author xusi
 * @since 2020-09-15
 */
public interface IBlogRoleToMenuService extends IService<BlogRoleToMenu> {
    List<MenuReader> getByRoleId(Long id);
}
