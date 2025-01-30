package com.example.cvcodeback;

import com.example.cvcodeback.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class MongoDbTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    //添加操作
    @Test
    void contextLoads() {
        Article article = new Article();
        article.setUid(1);
        article.setMain("测试文本");
        article.setTitle("测试标题");
        article.setStatus("0");
        article.setUp(0);
        article.setDown(0);
        Article article1 = mongoTemplate.insert(article);
        System.out.println(article1);
    }

}
