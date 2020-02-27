package com.hjh.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjh.community.dto.AccessTokenDTO;
import com.hjh.community.dto.GithubUser;
import com.hjh.community.mapper.UserMapper;
import com.hjh.community.model.User;
import com.hjh.community.provider.GithubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/*
    处理回调请求: 使用github登录时 github会回调此路径 获取code
    再次请求github获取access_token
    通过access_token获取用户信息 最后返回一个用户对象
 */

@Controller
@Slf4j
public class CallbackContorller {

    @Value("${github.clint.id}")
    String client_id;

    @Value("${github.client.secret}")
    String client_secret;

    @Value("${github.client.redirectUri}")
    String redireceUri;

    @Autowired
    GithubProvider githubClient;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/callback")
    public String Login(@RequestParam(name = "code")String code,
                        @RequestParam(name = "state")String state,
                        HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse
                        ){
        //获取github返回的code
//        log.info("code->"+code);
        //获取将code返回给github获取access_token
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(redireceUri);

        String access_token = githubClient.getAccessToken(accessTokenDTO);
        log.info("access_token->"+access_token);
        if (access_token == null){
            return "redirect:/";
        }
        GithubUser githubUser = githubClient.getGithubUserByAccessToken(access_token);

//        log.info("获取github用户信息成功");

        if(githubUser == null){
            //登录失败
            log.info("登录失败");
            return "/";
        }
        //登录成功获取cookie和session
        //httpServletRequest.getSession().setAttribute("user",githubUser);

        //如果githubUser.getId在数据库中有数据则直接返回对象即可
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account_no",String.valueOf(githubUser.getId()));
        User user;
        //用户存在则只更新token
        if ( (user = userMapper.selectOne(queryWrapper)) != null){
            user.setToken(UUID.randomUUID().toString());
            user.setGmtModify(System.currentTimeMillis());
            userMapper.updateById(user);
            log.info("查找user并只更新token"+user.toString());
        }else {
            //用户信息在表中不存在 则将用户保存到数据库
            user = new User();
            user.setAccountNo(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtModify(System.currentTimeMillis());
            user.setGmtCreate(System.currentTimeMillis());
            user.setBio(githubUser.getBio());
            log.info("新增user->"+user.toString());
            userMapper.insert(user);
        }

        //登录成功获取cookie和session
        httpServletRequest.getSession().setAttribute("user",user);
        //返回cookie
        httpServletResponse.addCookie(new Cookie("token",user.getToken()));

        //重定向 清除地址栏的请求信息
        log.info(githubUser.toString()+"获取session 重定向回index");
        return "redirect:/";
    }
}
