package com.example.cvcodeback.entity;

import com.example.cvcodeback.utils.AutoIncKey;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("article")
public class ResArticle {
    @Id
    @AutoIncKey
    private Integer id;
    private String nickName;
    private String title;
    // 点赞
    private Integer up;
    // 踩
    private Integer down;
    // 封面图片地址
    private String coverPath;
    // 文章标签
    private List<String> tags;
    private String info;
    private Boolean clickUp;
    private Boolean clickDown;
}
