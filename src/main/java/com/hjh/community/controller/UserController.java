package com.hjh.community.controller;

import com.hjh.community.service.QuestionService;
import com.hjh.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    //登出将cookie中的token去掉以及将session中的user对象去掉
    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        //从response中移除cookie
        httpServletResponse.addCookie(new Cookie("token",null));
        //从request中移除session
        httpServletRequest.getSession().removeAttribute("user");
        return "redirect:/";
    }

}
