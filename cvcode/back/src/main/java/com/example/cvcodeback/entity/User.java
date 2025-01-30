package com.example.cvcodeback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private String nickname;
    @TableField
    private String username;
    @TableField
    private String password;
    @TableField
    private String email;
    @TableField
    private String phone;
    @TableField
    private String type;
}
