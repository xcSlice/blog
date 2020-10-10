package com.xusi.blog.content.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 博客内容表
 * </p>
 *
 * @author xusi
 * @since 2020-09-14
 */
@ApiModel(value="BlogContent对象", description="博客内容表")
public class BlogContent implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "博文标题")
    private String contentTitle;

    @ApiModelProperty(value = "博客图片地址")
    private String contentImg;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改日期")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "作者")
    private String author;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BlogContent{" +
        "id=" + id +
        ", contentTitle=" + contentTitle +
        ", contentImg=" + contentImg +
        ", content=" + content +
        ", isDelete=" + isDelete +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        ", author=" + author +
        "}";
    }
}
