package com.hjh.community.controller;

import com.hjh.community.dao.UserDao;
import com.hjh.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
首页的请求映射 用于返回首页
 */
@Slf4j
@Controller
public class IndexController {

    @Autowired
    UserDao userDao;
    //首页
    @GetMapping("/")
    public String Index(HttpServletRequest httpServletRequest){
        //获取浏览器请求的cookie中的token 再去数据库中查找记录返回前端
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null){
            log.info("cookie为空");
            return "index";
        }
        for (Cookie tmpCookie:cookies){
            if (tmpCookie.getName().equals("token")){
                String token = tmpCookie.getValue();
                if (token == null){
                    log.info("token为空");
                    return "index";
                }
                log.info("token->"+token);
                User user = userDao.findByToken(token);
                if (user == null){
                    httpServletRequest.getSession().removeAttribute("user");
                    log.info("user对象为空");
                    return "index";
                }
                log.info("返回user对象"+user.toString());
                httpServletRequest.getSession().setAttribute("user",user);
            }
        }
        return "index";
    }

}
