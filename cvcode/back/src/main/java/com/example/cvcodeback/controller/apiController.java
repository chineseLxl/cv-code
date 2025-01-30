package com.example.cvcodeback.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api")
public class apiController {
    @GetMapping("/test-{name}")
    public String test(@PathVariable("name") String name) {
        return name;
    }
}
