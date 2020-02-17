package com.hjh.community.provider;

import com.alibaba.fastjson.JSON;
import com.hjh.community.dto.AccessTokenDTO;
import com.hjh.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
*   调用github的接口返回信息
*
*/

@Component
@Slf4j
public class GithubProvider {

    /*
    调用github的接口返回access_token
    parm: AccessTokenDTO
    return: access_token
     */

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        String responseMsg = "";
        // 使用OkHttp调用github的post请求获取access_token
        final MediaType head = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(head,JSON.toJSONString(accessTokenDTO));
        Request Request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(Request).execute()){
            responseMsg = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String accessToken = responseMsg.split("&")[0].split("=")[1];
        return accessToken;
    }

    /*
    通过access_token获取GithubUser对象
     */
    public GithubUser getGithubUserByAccessToken(String accessToken){
        GithubUser githubUser = null;
        String responseMsg = "";

        OkHttpClient client = new OkHttpClient();
        Request Request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(Request).execute()){
            responseMsg = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        githubUser = JSON.parseObject(responseMsg,GithubUser.class);
        return githubUser;
    }


}
