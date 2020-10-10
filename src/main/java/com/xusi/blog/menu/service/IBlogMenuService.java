package com.xusi.blog.menu.service;

import com.xusi.blog.menu.entity.BlogMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author xusi
 * @since 2020-09-15
 */
public interface IBlogMenuService extends IService<BlogMenu> {
    boolean checkIsEntityExistById(Long id);
    List<BlogMenu> getAllRoot();

    void saveByPidAndName(Long pid, String name);

    Object selectById(Long id);
}
