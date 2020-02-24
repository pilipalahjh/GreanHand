package com.hjh.community.service;

import com.hjh.community.dto.QuestionDTO;
import com.hjh.community.mapper.QuestionMapper;
import com.hjh.community.mapper.UserMapper;
import com.hjh.community.model.Question;
import com.hjh.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;

    public List<QuestionDTO> questionDTOList(){
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        List<Question> questions;
        questions = questionMapper.selectList(null);
        if (questions == null){
            log.info("question列表为空");
            return null;
        }

        //遍历questions集合
        for (Question tmpQuestion:questions){
            if (tmpQuestion.getCreator() == null){
                log.info("此条记录没有创建者"+tmpQuestion.toString());
            }else {
                User user;
                user = userMapper.selectById(tmpQuestion.getCreator());
                if (user == null){
                    log.info("根据creator未查找用户");
                    continue;
                }
                //给QuestionDTO赋值并加入集合
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setUser(user);
                questionDTO.setQuestion(tmpQuestion);
                questionDTOs.add(questionDTO);
            }
        }
        return questionDTOs;
    }
}
