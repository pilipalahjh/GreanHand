package com.hjh.community.interceptor;


import com.hjh.community.model.User;
import com.hjh.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //session中没有值则从cookie中token去数据库中获取user
        User user;
        user = (User) request.getSession().getAttribute("user");
        if (user != null){
            return true;
        }

        //user为空则从cookie中获取token然后去数据库中查出对象
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            log.info("cookie为空");
            return true;
        }

        //去cookie中token的内容去数据库中查找信息
        for (Cookie tmpCookie:cookies){
            if (tmpCookie.getName().equals("token")){
                String token = tmpCookie.getValue();
                if (token == null){
                    log.info("token为空");
                    return true;
                }
                log.info("token->"+token);
                user = userService.findByToken(token);
                if (user == null){
                    request.getSession().removeAttribute("user");
                    log.info("不正确的token返回user对象为空");
                    return true;
                }
                log.info("返回user对象"+user.toString());
                request.getSession().setAttribute("user",user);
                break;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
