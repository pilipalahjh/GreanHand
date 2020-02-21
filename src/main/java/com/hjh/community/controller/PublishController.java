package com.hjh.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish(){
        log.info("publish controller");
        return "publish";
    }
}
