package com.xusi.blog.menu.controller;


import com.xusi.blog.common.R;
import com.xusi.blog.menu.entity.BlogMenu;
import com.xusi.blog.menu.entity.MenuReader;
import com.xusi.blog.menu.service.IBlogMenuService;
import com.xusi.blog.menu.service.impl.BlogMenuServiceImpl;
import com.xusi.blog.security.service.IBlogRoleToMenuService;
import com.xusi.blog.security.service.impl.BlogRoleToMenuServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author xusi
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/menu")
@Api(value = "/menu",tags = "博客:菜单管理")
public class BlogMenuController {
    @Resource
    private IBlogMenuService menuService;
    @Resource
    private IBlogRoleToMenuService r2mService;


//    获取所有零级菜单
    @ApiOperation("所有零级菜单")
    @GetMapping("/all-root")
    public ResponseEntity<Object> getAllRoot(){

//        return R.ok(200).data("data",menuService.getAllRoot());
        System.out.println("get root");
        return ResponseEntity.ok(menuService.getAllRoot());
    }

//    获取所有的菜单, by role id
    @ApiOperation("根据角色id获取菜单")
    @GetMapping("/role/{role_id}")
    public R getAll(@PathVariable Long  role_id){
        //交由业务层处理
        List<MenuReader> finalNode = r2mService.getByRoleId(role_id);
        return R.ok(200).data("data",finalNode);
    }

    @ApiOperation("根据菜单id获取菜单")
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id){
        return R.ok(200).data("data",menuService.selectById(id));
    }


    /**
    * @Description: 新增菜单
    * @Param: [pid, name] -> 父菜单id,菜单名字
    * @return: com.xusi.blog.common.R
    * @Author: cw
    * @Date: 2020/9/15
    */
    @ApiOperation("根据父id和菜单名字新增菜单")
   @PostMapping
    public R insert(Long pid, String name){
        menuService.saveByPidAndName(pid, name);
        return R.ok(200).message("插入成功");

   }

   /**
   * @Description: 根据ID update
   * @Param: [id, menu]
   * @return: com.xusi.blog.common.R
   * @Author: xusi
   * @Date: 2020/9/15
   */
   @PutMapping
    public R update(@ApiParam("") Long id, BlogMenu menu){
        if(!menuService.checkIsEntityExistById(id)){
            return R.error(500).message("Menu is not exist");
        }
        menu.setGmtModified(null);
        menuService.updateById(menu);
        return R.ok(200).message("update success");
   }

   /**
   * @Description: del by id,if exist
   * @Param: [id]
   * @return: com.xusi.blog.common.R
   * @Author: xusi
   * @Date: 2020/9/15
   */
   @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id){
       // 检查是否存在
       if (menuService.checkIsEntityExistById(id)){
           menuService.removeById(id);
           return R.ok(200).message("delete success");
       } else {
           return R.error(500).message("menu no exist");
       }
   }


}

