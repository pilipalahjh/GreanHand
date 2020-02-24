package com.hjh.community.dto;

import com.hjh.community.model.Question;
import com.hjh.community.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class QuestionDTO{
    Question question;
    User user;
}
