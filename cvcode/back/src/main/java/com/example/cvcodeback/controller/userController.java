package com.example.cvcodeback.controller;

import com.example.cvcodeback.entity.User;
import com.example.cvcodeback.service.UserService;
import com.example.cvcodeback.utils.JsonResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class userController {
    final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "你好";
    }
    @PostMapping("/login")
    public JsonResult login(@RequestBody User user) {
        if (userService.login(user)) {

            HashMap<String,String> jsonMap = new HashMap<>();
            jsonMap.put("token", userService.resToken(user));
            return new JsonResult<>(jsonMap, "登录成功");
        } else {
            return new JsonResult<>("登录失败","-1");
        }
    }
    // 注册
    @PostMapping("/registered")
    public JsonResult registered(@RequestBody User user) {
        if (userService.registered(user)) {
            HashMap<String,String> jsonMap = new HashMap<>();
            jsonMap.put("token", userService.resToken(user));
            return new JsonResult<>(jsonMap, "注册成功");
        } else {
            return new JsonResult<>("注册失败","-1");
        }
    }

    // 获取用户信息 （解析token）
    @RequestMapping("/getNickName")
    public JsonResult getUserInfo(HttpServletRequest request) {
        System.out.println(request.getHeader("token"));
        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put("nickname", userService.getUserInfo(request.getHeader("token")).getNickname());
        return new JsonResult<>(jsonMap);
    }
}
