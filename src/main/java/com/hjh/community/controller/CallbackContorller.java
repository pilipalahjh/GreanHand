package com.hjh.community.controller;

import com.hjh.community.dto.AccessTokenDTO;
import com.hjh.community.dto.GithubUser;
import com.hjh.community.provider.GithubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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
    //根据配置文件获取client_id和client_secret
//    static {
//        Properties properties = new Properties();
//        try {
//            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
//            client_id = properties.getProperty("githubClint_id");
//            client_secret = properties.getProperty("githubClient_secret");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Autowired
    GithubProvider githubClient;

    @GetMapping("/callback")
    public String Login(@RequestParam(name = "code")String code,
                        @RequestParam(name = "state")String state,
                        HttpServletRequest httpServletRequest
                        ){
        //获取github返回的code
        log.info("code->"+code);
        //获取将code返回给github获取access_token
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(redireceUri);

        String access_token = githubClient.getAccessToken(accessTokenDTO);
        log.info("access_token->"+access_token);

        GithubUser githubUser = githubClient.getGithubUserByAccessToken(access_token);

        if(githubUser == null){
            //登录失败
            log.info(githubUser.toString()+"登录失败");
            return "/";
        }
        //登录成功获取cookie和session
        httpServletRequest.getSession().setAttribute("user",githubUser);



        //重定向 清除地址栏的请求信息
        log.info(githubUser.toString()+"获取session 重定向回index");
        return "redirect:/";
    }
}
