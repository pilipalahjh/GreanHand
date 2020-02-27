package com.hjh.community.utils;

import com.hjh.community.dto.PaginationDTO;
import com.hjh.community.dto.QuestionDTO;
import com.hjh.community.model.Question;
import com.hjh.community.model.User;
import com.hjh.community.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
public class ModelUtils {
    private ModelUtils(){}

    //将PaginationDTO的值分别放入Model
    public static void setQuestionPaginationDTO2Model(PaginationDTO<QuestionDTO> questionDTOPaginationDTO, Model model){
        List<Integer> pageList = questionDTOPaginationDTO.getPageList();
        List<QuestionDTO> questionDTOs;
        questionDTOs = questionDTOPaginationDTO.getList();
        model.addAttribute("questionDTOs",questionDTOs);
        model.addAttribute("pageList",pageList);
    }

    public static void setQuestionsByRequest(HttpServletRequest httpServletRequest, QuestionService questionService,Model model){
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        if (user == null){
            log.info("从session获取的user为空");
        }
        List<Question> questions;
        questions = questionService.getQuestionListByCreator(user.getId());

        model.addAttribute("questions",questions);
    }
}
