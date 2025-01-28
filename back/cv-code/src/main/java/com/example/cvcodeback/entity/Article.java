package com.example.cvcodeback.entity;

import com.example.cvcodeback.utils.AutoIncKey;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("article")
public class Article {
    @Id
    @AutoIncKey
    private Integer id;
    private Integer uid;
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
