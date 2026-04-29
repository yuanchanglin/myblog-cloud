package com.yuanchanglin.userservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Map<Long, Map<String, Object>> userDb = new HashMap<>();
    private Long idCounter = 1L;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> params) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", idCounter++);
        user.put("username", params.get("username"));
        user.put("password", params.get("password"));
        user.put("nickname", params.get("nickname"));
        userDb.put((Long) user.get("id"), user);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "注册成功");
        result.put("data", user);
        return result;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        Map<String, Object> result = new HashMap<>();
        for (Map<String, Object> user : userDb.values()) {
            if (user.get("username").equals(username) && user.get("password").equals(password)) {
                result.put("code", 200);
                result.put("message", "登录成功");
                result.put("data", user);
                return result;
            }
        }
        result.put("code", 401);
        result.put("message", "用户名或密码错误");
        return result;
    }

    @GetMapping("/info/{id}")
    public Map<String, Object> getUserInfo(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> user = userDb.get(id);
        if (user != null) {
            user.remove("password");
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", user);
        } else {
            result.put("code", 404);
            result.put("message", "用户不存在");
        }
        return result;
    }

    @PutMapping("/info/{id}")
    public Map<String, Object> updateUserInfo(@PathVariable Long id, @RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> user = userDb.get(id);
        if (user != null) {
            if (params.containsKey("nickname")) {
                user.put("nickname", params.get("nickname"));
            }
            if (params.containsKey("password")) {
                user.put("password", params.get("password"));
            }
            result.put("code", 200);
            result.put("message", "更新成功");
            result.put("data", user);
        } else {
            result.put("code", 404);
            result.put("message", "用户不存在");
        }
        return result;
    }
}
