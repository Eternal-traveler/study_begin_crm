package com.haoyang.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author haoyang
 * @reate 2022-07-30-17:57
 */


@Controller
public class WorkbenchindexController {

    @RequestMapping("/workbench/index.do")
    public String index(){
        return "/workbench/index";
    }
}
