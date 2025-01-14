package com.example.cvcodeback.controller;

import com.example.cvcodeback.entity.User;
import com.example.cvcodeback.service.UserService;
import com.example.cvcodeback.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
}
