package com.hjh.community.controller;

import com.hjh.community.service.QuestionService;
import com.hjh.community.utils.ModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PersonCenterController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/personCenter/{action}")
    //获取action中的值
    public String personCenterAction(@PathVariable(name="action")String action,
                                     HttpServletRequest httpServletRequest,
                                     Model model){
        String titleMsg = "";
        if (action.equals("questions")){
            titleMsg = "问题列表";
            //将questions放入model
            ModelUtils.setQuestionsByRequest(httpServletRequest,questionService,model);

        }

        if (action.equals("mymsg")){
            titleMsg = "我的信息";
        }

        if (action.equals("personmsg")){
            titleMsg = "个人信息";
        }

        model.addAttribute("selection",titleMsg);
        return "/personCenter";
    }

    @GetMapping("/personCenter")
    public String personCenter(HttpServletRequest httpServletRequest, Model model){
        //将questions放入model
        if (httpServletRequest.getSession().getAttribute("user") == null){
            return "redirect:/";
        }
        ModelUtils.setQuestionsByRequest(httpServletRequest,questionService,model);
        return "/personCenter";
    }
}
