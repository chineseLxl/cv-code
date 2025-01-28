package com.example.cvcodeback.entity;

import lombok.Data;

import java.util.List;

@Data
public class ResArticle {
    private Integer id;
    private String nickName;
    private String title;
    // 前端html元素
    private String main;
    // 审核状态
    private String status;
    // 点赞
    private Integer up;
    // 踩
    private Integer down;
    // 封面图片地址
    private String coverPath;
    // 文章标签
    private List<String> tags;
    // 文章创建id
    private String openId;
}
