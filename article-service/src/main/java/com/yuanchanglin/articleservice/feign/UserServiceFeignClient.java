package com.yuanchanglin.articleservice.feign;


import com.yuanchanglin.articleservice.feign.factory.UserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",fallbackFactory=UserServiceFallbackFactory.class)
public interface UserServiceFeignClient {

    @GetMapping("/user/t1") // 根据 user-service 实际接口路径调整
    String t1();

    @GetMapping("/user/p1")
    String p1(@RequestParam("userId") String userId); // 请求带参数

    @GetMapping("/user/error")
    String error();

}

