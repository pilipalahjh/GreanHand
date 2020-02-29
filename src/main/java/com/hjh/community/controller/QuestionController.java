package com.hjh.community.controller;

import com.hjh.community.dto.QuestionDTO;
import com.hjh.community.exception.CustomizeErrorEnum;
import com.hjh.community.exception.CustomizeException;
import com.hjh.community.mapper.QuestionMapper;
import com.hjh.community.model.Question;
import com.hjh.community.model.User;
import com.hjh.community.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;

    //进入问题提交页面
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    //获取问题详情
    @GetMapping("/question/{id}")
    public String questionGet(@PathVariable(name = "id") int questionId, Model model){
        QuestionDTO questionDTO;
        questionDTO = questionService.getQuestionDTOById(questionId);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }

    //添加question
    @PostMapping("/question")
    public String doPublish(@RequestParam(name="title")String title,
                            @RequestParam(name="msg")String msg,
                            @RequestParam(name="tag")String tag,
                            HttpServletRequest httpServletRequest,
                            Model model
    ){
        User user =  (User)httpServletRequest.getSession().getAttribute("user");

        if (user == null){
            log.info("从session获取user对象为空");
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setMsg(msg);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModify(System.currentTimeMillis());
        questionMapper.insert(question);
        log.info("添加问题成功->"+question.toString());
        return "redirect:/";
    }

    @GetMapping("/questionDelete/{id}")
    public String questionDelete(@PathVariable int id){
        int flag = questionService.deleteById(id);
        if (flag != 1){
            throw new CustomizeException(CustomizeErrorEnum.DELETE_QUESTION_FAILURE);
        }
        return "redirect:/";
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
        return "redirect:/";
    }

}
