package com.hjh.community.dao;

import com.hjh.community.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
    User findByToken(String token);
    int removeByToken(String token);
}
