package com.xusi.blog.menu.entity;

import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description: 菜单递归查询类
 * @author: xusi
 * @create:2020-09-15 13:02
 **/
public class MenuReader {
    private BlogMenu menu;

    private List<MenuReader> children;

    public MenuReader() {}
    public MenuReader(BlogMenu menu){
        this.menu = menu;
    }

    public BlogMenu getMenu() {
        return menu;
   }

    public void setMenu(BlogMenu menu) {
        this.menu = menu;
    }

    public List<MenuReader> getChildren() {
        return children;
    }

    public void setChildren(List<MenuReader> children) {
        this.children = children;
    }

}
