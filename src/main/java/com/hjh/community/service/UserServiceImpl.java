package com.hjh.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjh.community.mapper.UserMapper;
import com.hjh.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Override
    public User findByToken(String token) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("token",token);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    //清空用户的token
    @Override
    public int clearToken(User user) {
        int flag = 0;
        user.setToken("");
        flag = userMapper.updateById(user);
        return flag;
    }

    @Override
    public int clearToken(String token) {
        int flag = 0;
        User user = findByToken(token);
        flag = clearToken(user);
        return flag;
    }


}
