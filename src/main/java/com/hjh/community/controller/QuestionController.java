package com.hjh.community.controller;

import com.hjh.community.exception.CustomizeErrorEnum;
import com.hjh.community.exception.CustomizeException;
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
        int flag = questionService.deleteById(id);
        if (flag != 1){
            throw new CustomizeException(CustomizeErrorEnum.DELETE_QUESTION_FAILURE);
        }
        return "redirect:/personCenter";
    }

    @PostMapping("/questionUpdate")
    public String questionUpdate(@RequestParam int id,
                                 @RequestParam String title,
                                 @RequestParam String msg,
                                 @RequestParam String tag){
        int flag = questionService.updateById(id,title,msg,tag);
        if (flag != 1){
            throw new CustomizeException(CustomizeErrorEnum.UPDATE_QUESTION_FAILURE);
        }
        return "redirect:/personCenter";
    }

}
