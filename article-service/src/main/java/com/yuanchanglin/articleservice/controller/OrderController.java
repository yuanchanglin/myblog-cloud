package com.yuanchanglin.articleservice.controller;

import com.yuanchanglin.articleservice.feign.UserServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author y
 * @date 2026/1/26 11:18
 */

@RestController
@RequestMapping("/article")
public class OrderController {

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;


    @GetMapping("/testFeign1")
    public String testFeign1() {
        // 通过 Feign Client 调用 user-service 的 /info 接口
        String userInfo = userServiceFeignClient.t1();
        return "Order Service calling User Service: " + userInfo;
    }
    @GetMapping("/testFeign2")
    public String testFeign2() {
        // 通过 Feign Client 调用 user-service 的 /info 接口
        String userInfo = userServiceFeignClient.p1("你好");
        return "Order Service calling User Service: " + userInfo;
    }
    @GetMapping("/testFeign3")
    public String testFeign3() {
        // 通过 Feign Client 调用 user-service 的 /info 接口
        String userInfo = userServiceFeignClient.error();
        return "Order Service calling User Service: " + userInfo;
    }
}
