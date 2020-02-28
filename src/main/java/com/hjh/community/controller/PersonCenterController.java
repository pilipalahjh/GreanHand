package com.hjh.community.controller;

import com.hjh.community.exception.CustomizeErrorEnum;
import com.hjh.community.exception.CustomizeException;
import com.hjh.community.mapper.QuestionMapper;
import com.hjh.community.model.Question;
import com.hjh.community.service.QuestionService;
import com.hjh.community.utils.ModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class PersonCenterController {
    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;

    //进入个人中心首页
    @GetMapping("/personCenter")
    public String personCenter(HttpServletRequest httpServletRequest, Model model){
        //将questions放入model
        if (httpServletRequest.getSession().getAttribute("user") == null){
            return "redirect:/";
        }
        ModelUtils.setQuestionsByRequest(httpServletRequest,questionService,model);
        return "/personCenter";
    }

    //个人中心每个选项的跳转
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

    //个人中心点击question展示的信息
    @GetMapping("/personCenter/question/{id}")
    public String personQuestion(@PathVariable int id,Model model){
        Question question = questionMapper.selectById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorEnum.QUESTION_NOT_FOUND);
        }
        model.addAttribute("selection","问题详情");
        model.addAttribute("question",question);
        return "/personCenter";
    }
}
