package com.hjh.community.controller;

import com.hjh.community.dto.PaginationDTO;
import com.hjh.community.service.UserService;
import com.hjh.community.dto.QuestionDTO;
import com.hjh.community.service.QuestionService;
import com.hjh.community.utils.ModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

/*
首页的请求映射 用于返回首页
 */
@Slf4j
@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    PaginationDTO<QuestionDTO> questionDTOPaginationDTO;
    //首页
    @GetMapping("/")
    public String Index(Model model){
        //将查出来的问题列表返回前端 问题及用户
        List<QuestionDTO> questionDTOs;
//        questionDTOs = questionService.questionDTOList();

        //增加分页功能 将QuestionDTO和页码集合返回前端
        //一页显示的数量
        int size = 3;
        //当前页数
        int currentPage = 1;
        questionDTOPaginationDTO = questionService.getPaginationDTO(currentPage,size);
        List<Integer> pageList = questionDTOPaginationDTO.getPageList();
        questionDTOs = questionDTOPaginationDTO.getList();

        model.addAttribute("questionDTOs",questionDTOs);
        model.addAttribute("pageList",pageList);
        model.addAttribute("currentPage",currentPage);

        return "index";
    }

    //分页相关
    //分页查询QuestionDTO
    @GetMapping("/questionPage/{page}")
    public String questionDTOPage(@PathVariable int page,Model model){
        //一页显示的数量
        int size = 3;
        questionDTOPaginationDTO = questionService.getPaginationDTO(page,size);
        model.addAttribute("currentPage",page);
        //将 questionDTOPaginationDTO 中的值分别取值放入model
        ModelUtils.setQuestionPaginationDTO2Model(questionDTOPaginationDTO,model);
        return "index";
    }
    //上一页
    @GetMapping("/questionPageLast")
    public String questionDTOPage(Model model){
        int currentPage;
        int pageSize;
        currentPage = questionDTOPaginationDTO.getCurrentPage();
        pageSize = questionDTOPaginationDTO.getPageSize();
        //有上一页
        if (questionDTOPaginationDTO.isHasPrevious()){
            questionDTOPaginationDTO = questionService.getPaginationDTO(currentPage-1,pageSize);
        }else {
            //没有上一页 返回首页
            questionDTOPaginationDTO = questionService.getPaginationDTO(1,pageSize);
        }
        currentPage = questionDTOPaginationDTO.getCurrentPage();
        model.addAttribute("currentPage",currentPage);
        //将 questionDTOPaginationDTO 中的值分别取值放入model
        ModelUtils.setQuestionPaginationDTO2Model(questionDTOPaginationDTO,model);
        return "index";
    }
    //下一页
    @GetMapping("/questionPageNext")
    public String questionDTOPageNext(Model model){
        int currentPage;
        int pageSize;
        currentPage = questionDTOPaginationDTO.getCurrentPage();
        pageSize = questionDTOPaginationDTO.getPageSize();
        //有上一页
        if (questionDTOPaginationDTO.isHasNext()){
            questionDTOPaginationDTO = questionService.getPaginationDTO(currentPage+1,pageSize);
        }else {
            //没有上一页 返回首页
            questionDTOPaginationDTO = questionService.getPaginationDTO(1,pageSize);
        }
        currentPage = questionDTOPaginationDTO.getCurrentPage();
        model.addAttribute("currentPage",currentPage);
        //将 questionDTOPaginationDTO 中的值分别取值放入model
        ModelUtils.setQuestionPaginationDTO2Model(questionDTOPaginationDTO,model);
        return "index";
    }

}
