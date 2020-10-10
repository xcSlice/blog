package com.xusi.blog.content.controller;


import com.xusi.blog.common.R;
import com.xusi.blog.content.entity.BlogContent;
import com.xusi.blog.content.service.IBlogContentService;
import com.xusi.blog.content.service.impl.BlogContentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 博客内容表 前端控制器
 * </p>
 *
 * @author xusi
 * @since 2020-09-14
 */
@RestController
@RequestMapping("/api/content")
@Api(value = "/content",tags = {"博客:博文管理"})
public class BlogContentController {

    @Resource
    private IBlogContentService contentService;
    private static final Logger log = LoggerFactory.getLogger(BlogContentController.class);



    @GetMapping
    @ApiOperation("获取全部博客")
    public R getAll(){
        List<BlogContent> contentList = contentService.selectAll();
        return R.ok(200).data("data",contentList);
    }

//    select by id
    @GetMapping("/{id}")
    @ApiOperation("根据id获取")
    public R getOne(@PathVariable Long id){
//        不存在
        if(!contentService.entityIsExist(id)){
            return R.error(500).message("entity no exist");
        }
        return R.ok(200).data("data",contentService.selectById(id));
    }

//    select by list<id>
    @GetMapping("/ids")
    @ApiOperation("根据传入的 id 数组来获取博客")
    public R getByIdList(Long[] ids){
        List<BlogContent> contentList = new ArrayList<>();
//        不为空
        if (ids != null) {
            contentService.listByIds(Arrays.asList(ids));
        }
        return R.ok(200).data("data",contentList);
    }


    @PostMapping
    @ApiOperation("新增一个博文")
    public R insert(BlogContent content){
        contentService.save(content);
        return R.ok(200);
    }

//    更新
    @PutMapping("/{id}")
    @ApiOperation("根据id来更新博文")
    public R update(@PathVariable Long id, BlogContent content){
        //        不存在
        if(!contentService.entityIsExist(id)){
            return R.error(200).message("entity no exist");
        }
        BlogContent content4DB = contentService.getById(id);
//        if db id != id
        if(!content4DB.getId().equals(id)){
            return R.error(500).message("data id not equals id");
        }
//        设置更新时间为null, 自动填充才能启用
        content.setGmtModified(null);
        contentService.updateById(content);
        return R.ok(200);
    }

//    删除 by id
    @ApiOperation("根据id删除")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id){
        //        不存在
        if(!contentService.entityIsExist(id)){
            return R.error(200).message("entity no exist");
        }
        contentService.removeById(id);
        return R.ok(200);
    }

//    delete by List<id>
    @DeleteMapping("/ids")
    @ApiOperation("根据传入的 ID 数组来删除")
    public R deleteByIdList(List<Long> ids){
        if (ids != null) {
            contentService.removeByIds(ids);
        }
        return R.ok(200);
    }


}

