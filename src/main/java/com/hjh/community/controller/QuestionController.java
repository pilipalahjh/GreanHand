package com.hjh.community.controller;

import com.hjh.community.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/questionDelete")
    public String questionDelete(@RequestParam int id){
        questionService.deleteById(id);
        return "redirect:/personCenter";
    }

    @PostMapping("/questionUpdate")
    public String questionUpdate(@RequestParam int id,
                                 @RequestParam String title,
                                 @RequestParam String msg,
                                 @RequestParam String tag){
        log.info("id->"+id);
        questionService.updateById(id,title,msg,tag);
        return "redirect:/personCenter";
    }

}
