package com.example.cvcodeback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cvcodeback.dao.UserMapper;
import com.example.cvcodeback.entity.User;
import com.example.cvcodeback.service.UserService;
import com.example.cvcodeback.utils.PasswordMd5;
import com.example.cvcodeback.utils.TokenUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserImpl implements UserService {
    final UserMapper userMapper;
    final PasswordMd5 passwordMd5;
    final TokenUtil tokenUtil;

    public UserImpl(UserMapper userMapper, PasswordMd5 passwordMd5, TokenUtil tokenUtil) {
        this.userMapper = userMapper;
        this.passwordMd5 = passwordMd5;
        this.tokenUtil = tokenUtil;
    }

    @Override
    public String resToken(User user) {
        User loginUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        return tokenUtil.getToken(Integer.toString(loginUser.getId()),loginUser.getUsername(),loginUser.getNickname());
    }

    @Override
    public Boolean login(User user) {
        User loginUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (loginUser != null) {
            return loginUser.getPassword().equals(passwordMd5.encode(user.getPassword()));
        }
        return false;
    }

    @Override
    public User getUserInfo(String token) {
        Map<String,String> tokenMap = tokenUtil.parseToken(token);
        Integer uid = Integer.parseInt(tokenMap.get("userId"));
        return userMapper.selectOne(new QueryWrapper<User>().eq("id", uid));
    }

    @Override
    public Boolean registered(User user) {
        if (userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername())) == null) {
            System.out.println(111);
            user.setPassword(passwordMd5.encode(user.getPassword()));
            userMapper.insert(user);
            return true;
        }
        return false;
    }

}
