package com.example.cvcodeback.service;

import com.example.cvcodeback.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    void UploadArticle(Article article);
    List<ResArticle> getArticleByStatus(String status);
    List<ArticleRange> getRangeAll();
    List<ArticleType> getTypeAll();
    List<ArticleMenu> getArticleMenu();
    Boolean existArticleByOpenId(String openId);
    ResArticleInfo getArticleInfoById(Integer id);
    List<ResArticle> getArticlePage(Integer page, Integer mid, Integer index);
}
