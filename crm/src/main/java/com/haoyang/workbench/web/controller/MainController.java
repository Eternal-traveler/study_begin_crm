package com.haoyang.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hao yang
 * @date 2022-08-02-15:30
 */

@Controller
public class MainController {

    @RequestMapping("/workbench/main/index.do")
    public String index(){
        return "/workbench/main/index";
    }
}
