package com.haoyang.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haoyang
 * @reate 2022-07-27-17:12
 */

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        //请求转发，测试成功
        return "index";
    }
}
