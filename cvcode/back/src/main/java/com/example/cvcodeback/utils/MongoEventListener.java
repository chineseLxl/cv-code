package com.example.cvcodeback.utils;

import com.example.cvcodeback.entity.Article;
import com.example.cvcodeback.entity.IncInfo;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class MongoEventListener extends AbstractMongoEventListener<Object> {
    final MongoTemplate mongoTemplate;

    public MongoEventListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Article source = (Article) event.getSource();
        if (source != null) {
            ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
                @Override
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    ReflectionUtils.makeAccessible(field);
                    if (field.isAnnotationPresent(AutoIncKey.class)) {
                        field.set(source, getNextId(source.getClass().getSimpleName()));
                    }
                }
            });
        }
    }
    private Integer getNextId(String collName) {
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("incId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        IncInfo inc= mongoTemplate.findAndModify(query, update, options, IncInfo.class);
        return inc.getIncId();
    }
}
