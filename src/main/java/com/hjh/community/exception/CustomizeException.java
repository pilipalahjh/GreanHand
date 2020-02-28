package com.hjh.community.exception;

/**
 * @author hujiaheng
 * @date 2020/2/28 15:41
 *
 * 自定义异常
 */
public class CustomizeException extends RuntimeException {

    String msg;

    public CustomizeException(CustomizeErrorEnum errorEnum){
        super(errorEnum.getMsg());
        this.msg = errorEnum.getMsg();
    }

    public String getMsg(){
        return this.msg;
    }

}
