package com.hjh.community.service;

import com.hjh.community.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User findByToken(String token);
    int clearToken(User user);
    int clearToken(String token);
}
