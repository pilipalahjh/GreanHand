package com.hjh.community.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjh.community.mapper.UserMapper;
import com.hjh.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDaoImpl implements UserDao{
    @Autowired
    UserMapper userMapper;
    @Override
    public User findByToken(String token) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("token",token);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public int RemoveByToken(String token) {
        int flag = 0;
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("token",token);
        flag = userMapper.delete(queryWrapper);
        return flag;
    }
}
