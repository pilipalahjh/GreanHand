package com.hjh.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjh.community.dto.PaginationDTO;
import com.hjh.community.dto.QuestionDTO;
import com.hjh.community.exception.CustomizeErrorEnum;
import com.hjh.community.exception.CustomizeException;
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

    //根据creator获取Question列表
    public List<Question> getQuestionListByCreator(int userId){
        List<Question> questions;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("creator", userId);
        questions = questionMapper.selectList(queryWrapper);
        return questions;
    }

    //根据questionId获取此问题信息以及创建者信息
    public QuestionDTO getQuestionDTOById(int questionId){
        QuestionDTO questionDTO = new QuestionDTO();
        Question question;
        User user;

        question = questionMapper.selectById(questionId);
        if (question == null){
            throw new CustomizeException(CustomizeErrorEnum.QUESTION_NOT_FOUND);
        }

        user = userMapper.selectById(question.getCreator());

        if (user == null){
            log.info("此问题找不到创建者");
        }

        questionDTO.setQuestion(question);
        questionDTO.setUser(user);

        return questionDTO;
    }

    //总页数 输入参数 一页显示的数量
    public int getQuestionPageCount(int size){
        int page = 0;
        int allCount = 0;
        allCount = questionMapper.selectCount(null);
        double tmp = allCount*1.0/size;
        page = (int) Math.ceil(tmp);
        return page;
    }

    //获取question的分页对象
    public IPage<Question> getIPageQuestion(int currentPage,int size){
        Page<Question> page = new Page<>(currentPage,size);
        IPage<Question> questionIPage = questionMapper.selectPage(page,null);
        return questionIPage;
    }

    //通过question的分页对象获取questionDTO的分页对象
    public PaginationDTO<QuestionDTO> getPaginationDTO(int currentPage,int size){
        //页数
        int pageCount;
        IPage<Question> questionIPage = getIPageQuestion(currentPage,size);
        pageCount = (int)questionIPage.getPages();
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        //遍历 questions获取QuestionDTO集合
        List<Question> questions = questionIPage.getRecords();

        for (Question tmpQuestion:questions){
            QuestionDTO questionDTO = getQuestionDTOById(tmpQuestion.getId());
            questionDTOs.add(questionDTO);
        }
        PaginationDTO<QuestionDTO> questionDTOPaginationDTO = new PaginationDTO<>(questionDTOs,currentPage,pageCount,size);
        return questionDTOPaginationDTO;
    }

    public int deleteById(int id){
        int i = 0;
        i = questionMapper.deleteById(id);
        return i;
    }

    public int updateById(int id,String title,String msg,String tag){
        int flag = 0;
        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setMsg(msg);
        question.setTag(tag);
        question.setGmtModify(System.currentTimeMillis());
        flag = questionMapper.updateById(question);
        return flag;
    }
}
