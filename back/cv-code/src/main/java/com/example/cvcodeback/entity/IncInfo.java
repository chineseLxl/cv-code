package com.example.cvcodeback.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "inc")
public class IncInfo {

    @Id
    private String id;// 主键
    @Field
    private String collName;// 需要自增id的集合名称
    @Field
    private Integer incId;// 当前自增id值

}
