package com.hjh.community.controller;

import com.hjh.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class UserController {
    @Autowired
    UserService userService;

    //登出将cookie中的token去掉以及将session中的user对象去掉
    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie tmpCookie:cookies){
            if (tmpCookie.getName().equals("token")){
                if (tmpCookie.getValue() != null) {
//                    userDao.removeByToken(tmpCookie.getValue());
                      String token = tmpCookie.getValue();
                      log.info("token->"+token);
                      userService.clearToken(token);
                      tmpCookie.setValue(null);
                }
                break;
            }
        }
        httpServletRequest.getSession().removeAttribute("user");
        return "redirect:/";
    }


}
