package com.hjh.community.controller;

import com.hjh.community.mapper.QuestionMapper;
import com.hjh.community.model.Question;
import com.hjh.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @Autowired
    QuestionMapper questionMapper;
    //将前端提交的问题信息写入数据库
    @PostMapping("/publish")
    public String doPublish(@RequestParam(name="title")String title,
                            @RequestParam(name="msg")String msg,
                            @RequestParam(name="tag")String tag,
                            HttpServletRequest httpServletRequest,
                            Model model
                            ){
        User user =  (User)httpServletRequest.getSession().getAttribute("user");

        if (user == null){
            log.info("从session获取user对象为空");
            model.addAttribute("erro","用户未登录");
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
}
