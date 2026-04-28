package com.yuanchanglin.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author y
 * @date 2026/1/23 17:39
 */

@RestController
@RequestMapping("/user")
public class TestController {

    @GetMapping("/t1")
    public String test1(){
        return "OK";
    }

    @GetMapping("/p1")
    public String test1(String userId){
        return "收到参数:"+userId;
    }

    @GetMapping("/error")
    public String error(){
        int i = new Random().nextInt();
        System.out.println("随机生成i:"+i);
        if(i%2==0){
            throw new RuntimeException("触发");
        }
        return "有的";
    }

}
