package com.example.cvcodeback.service;

import com.example.cvcodeback.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    Boolean registered(User user);
    String resToken(User user);
    Boolean login(User user);
    User getUserInfo(String token);
}
