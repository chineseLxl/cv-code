package com.example.cvcodeback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article_tag")
public class ArticleTag {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private String name;
    @TableField
    private Integer menuId;
}
