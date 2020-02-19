package com.hjh.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
首页的请求映射 用于返回首页
 */
@Controller
public class HelloController {
    @GetMapping("/get")
    public String Hello(@RequestParam String name, Model model){
        model.addAttribute("name",name);
        return "index";
    }

    @GetMapping("/")
    public String Index(Model model){
        model.addAttribute("name","index");
        return "index";
    }

}
