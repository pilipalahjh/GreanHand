package com.hjh.community.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("question")
public class Question {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    @TableField("creator")
    Integer creator;
    @TableField("title")
    String title;
    @TableField("msg")
    String msg;
    @TableField("tag")
    String tag;
    @TableField("like_count")
    Integer likeCount;
    @TableField("view_count")
    Integer viewCount;
    @TableField("comment_count")
    Integer commentCount;
    @TableField("gmt_create")
    long gmtCreate;
    @TableField("gmt_modify")
    long gmtModify;
}
