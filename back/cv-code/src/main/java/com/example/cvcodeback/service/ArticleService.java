package com.example.cvcodeback.service;

import com.example.cvcodeback.entity.Article;
import com.example.cvcodeback.entity.ArticleMenu;
import com.example.cvcodeback.entity.ArticleRange;
import com.example.cvcodeback.entity.ArticleType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    void UploadArticle(Article article);
    List<Article> getArticleByStatus(String status);
    List<ArticleRange> getRangeAll();
    List<ArticleType> getTypeAll();
    List<ArticleMenu> getArticleMenu();
    Boolean existArticleByOpenId(String openId);
}
