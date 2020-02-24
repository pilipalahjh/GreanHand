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

    //根据token在数据库中将信息去掉
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
                      httpServletRequest.removeAttribute("user");
                }
                break;
            }
        }
        return "redirect:/";
    }

}
