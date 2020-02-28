package com.hjh.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hujiaheng
 * @date 2020/2/28 19:06
 * 用于返回异常页面
 */

@Slf4j
@Controller
public class CustomizeErrorController implements ErrorController {
    //返回错误页面

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        log.info("statusCode->"+statusCode);
        return "/error";
    }

    @Override
    public String getErrorPath() {
        log.info("返回异常页面");
        return "/error";
    }
}
