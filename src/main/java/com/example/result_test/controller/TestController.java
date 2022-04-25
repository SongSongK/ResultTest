package com.example.result_test.controller;

import com.example.result_test.base.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kss
 * @date 2022/04/25 11:48
 **/
@RestController
public class TestController {
    @GetMapping("/hello")
    public ResultData<String> getStr() {
        return ResultData.success("hello,123");
    }

    @GetMapping("/hello2")
    public String getStr2() {
        return "hello,222";
    }

    @GetMapping("/wrong")
    public int error(){
        int i = 9/0;
        return i;
    }

    @GetMapping("error1")
    public void empty(){
        throw  new RuntimeException("参数不正确");
    }
}