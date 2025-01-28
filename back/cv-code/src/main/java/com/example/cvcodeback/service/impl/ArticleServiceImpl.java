package com.example.cvcodeback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cvcodeback.dao.ArticleMenuMapper;
import com.example.cvcodeback.dao.ArticleRangeMapper;
import com.example.cvcodeback.dao.ArticleTagMapper;
import com.example.cvcodeback.dao.ArticleTypeMapper;
import com.example.cvcodeback.entity.Article;
import com.example.cvcodeback.entity.ArticleMenu;
import com.example.cvcodeback.entity.ArticleRange;
import com.example.cvcodeback.entity.ArticleType;
import com.example.cvcodeback.service.ArticleService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleServiceImpl implements ArticleService {
    final MongoTemplate mongoTemplate;
    final ArticleRangeMapper articleRangeMapper;
    final ArticleTypeMapper articleTypeMapper;
    final ArticleMenuMapper articleMenuMapper;
    final ArticleTagMapper articleTagMapper;

    public ArticleServiceImpl(MongoTemplate mongoTemplate, ArticleRangeMapper articleRangeMapper, ArticleTypeMapper articleTypeMapper, ArticleMenuMapper articleMenuMapper, ArticleTagMapper articleTagMapper) {
        this.mongoTemplate = mongoTemplate;
        this.articleRangeMapper = articleRangeMapper;
        this.articleTypeMapper = articleTypeMapper;
        this.articleMenuMapper = articleMenuMapper;
        this.articleTagMapper = articleTagMapper;
    }

    @Override
    public void UploadArticle(Article article) {
        mongoTemplate.insert(article);
    }

    @Override
    public List<Article> getArticleByStatus(String status) {
        return mongoTemplate.find(new Query(Criteria.where("status").is(status)), Article.class);
    }

    @Override
    public List<ArticleRange> getRangeAll() {
        return articleRangeMapper.selectList(new QueryWrapper<ArticleRange>());
    }

    @Override
    public List<ArticleType> getTypeAll() {
        return articleTypeMapper.selectList(null);
    }

    @Override
    public List<ArticleMenu> getArticleMenu() {
        List<ArticleMenu> articleMenuList = articleMenuMapper.selectList(null);
        for (ArticleMenu articleMenu: articleMenuList) {
            articleMenu.setTags(articleMenuMapper.queryTagsByMid(articleMenu.getId()));
        }
        return articleMenuList;
    }

    @Override
    public Boolean existArticleByOpenId(String openId) {
        return mongoTemplate.find(new Query(Criteria.where("openId").is(openId)), Article.class).size() > 0;
    }

}
