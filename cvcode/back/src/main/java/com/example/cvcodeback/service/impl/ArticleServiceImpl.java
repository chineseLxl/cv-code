package com.example.cvcodeback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cvcodeback.dao.ArticleMenuMapper;
import com.example.cvcodeback.dao.ArticleRangeMapper;
import com.example.cvcodeback.dao.ArticleTagMapper;
import com.example.cvcodeback.dao.ArticleTypeMapper;
import com.example.cvcodeback.entity.*;
import com.example.cvcodeback.service.ArticleService;
import org.springframework.data.domain.Sort;
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
    public List<ResArticle> getArticleByStatus(String status) {
        return mongoTemplate.find(new Query(Criteria.where("status").is(status)), ResArticle.class);
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

    @Override
    public ResArticleInfo getArticleInfoById(Integer id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), ResArticleInfo.class);
    }

    @Override
    public List<ResArticle> getArticlePage(Integer page, Integer mid, Integer index) {
        if (mid == 0) {
            mid = mongoTemplate.findOne(new Query(Criteria.where("collName").is("Article")), IncInfo.class)
                    .getIncId() + 1;
        }
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC,"id"));
        query.addCriteria(Criteria.where("id").lt(mid)).limit(5);
        return mongoTemplate.find(query, ResArticle.class);
    }

}
