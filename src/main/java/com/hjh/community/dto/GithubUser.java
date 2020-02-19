package com.hjh.community.dto;

import lombok.Data;

/*
GithubUser的对象 用于保存从github返回的值
 */
@Data
public class GithubUser {
    private Long id;
    private String name;
    private String bio;
}
