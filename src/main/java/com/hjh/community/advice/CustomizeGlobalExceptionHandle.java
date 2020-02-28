package com.hjh.community.advice;

import com.hjh.community.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @author hujiaheng
 * @date 2020/2/28 16:04
 */
@ControllerAdvice
@Slf4j
//该注解可以处理请求异常
public class CustomizeGlobalExceptionHandle {

    //处理所有的异常
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGlobalException(HttpServletRequest request,Model model, Throwable ex){
        HttpStatus httpStatus = getStatus(request);

        log.info("进入全局异常处理类 statusCode->"+httpStatus);

        if (httpStatus.is4xxClientError()){
            model.addAttribute("error","客户端错误!!");
        }

        if (httpStatus.is5xxServerError()){
            model.addAttribute("error","服务器错误!!");
        }

        if (ex instanceof CustomizeException){
            model.addAttribute("error",ex.getMessage());
        }
        return new ModelAndView("/error");
    }

    //获取返回信息
    public HttpStatus getStatus(HttpServletRequest request){
        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (code == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }else{
            return HttpStatus.valueOf(code);
        }
    }

}
