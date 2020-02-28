package com.hjh.community.exception;

/**
 * @author hujiaheng
 * @date 2020/2/28 15:44
 */
public enum CustomizeErrorEnum {

    QUESTION_NOT_FOUND("此问题未找到,换个问题重试"),
    DELETE_QUESTION_FAILURE("删除问题失败"),
    UPDATE_QUESTION_FAILURE("更新问题失败");


    private String msg;

    CustomizeErrorEnum(String msg){
        this.msg = msg;
    }

    String getMsg(){
        return this.msg;
    }

}
