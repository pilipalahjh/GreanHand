package com.hjh.community.controller;

import com.hjh.community.dto.PaginationDTO;
import com.hjh.community.service.UserService;
import com.hjh.community.dto.QuestionDTO;
import com.hjh.community.model.User;
import com.hjh.community.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    public String Index(HttpServletRequest httpServletRequest,Model model){
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
        //session中有User对象就不需要去数据库中查找了 提高效率
        if (httpServletRequest.getSession().getAttribute("user") != null){
            return "index";
        }

        //获取浏览器请求的cookie中的token 再去数据库中查找记录返回前端
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null){
            log.info("cookie为空");
            return "index";
        }
        //去cookie中token的内容去数据库中查找信息
        for (Cookie tmpCookie:cookies){
            if (tmpCookie.getName().equals("token")){
                String token = tmpCookie.getValue();
                if (token == null){
                    log.info("token为空");
                    return "index";
                }
                log.info("token->"+token);
                User user = userService.findByToken(token);
                if (user == null){
                    httpServletRequest.getSession().removeAttribute("user");
                    log.info("不正确的token返回user对象为空");
                    return "index";
                }
                log.info("返回user对象"+user.toString());
                httpServletRequest.getSession().setAttribute("user",user);
                break;
            }
        }
        return "index";
    }

    //分页查询QuestionDTO
    @GetMapping("/questionDTOPage")
    public String questionDTOPage(@RequestParam int page,Model model){
        //一页显示的数量
        int size = 3;
        questionDTOPaginationDTO = questionService.getPaginationDTO(page,size);
        List<Integer> pageList = questionDTOPaginationDTO.getPageList();
        List<QuestionDTO> questionDTOs = questionDTOPaginationDTO.getList();

        model.addAttribute("questionDTOs",questionDTOs);
        model.addAttribute("pageList",pageList);
        model.addAttribute("currentPage",page);
        return "index";
    }

    @GetMapping("/questionDTOPageLast")
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
        List<Integer> pageList = questionDTOPaginationDTO.getPageList();
        List<QuestionDTO> questionDTOs;
        questionDTOs = questionDTOPaginationDTO.getList();
        currentPage = questionDTOPaginationDTO.getCurrentPage();
        model.addAttribute("questionDTOs",questionDTOs);
        model.addAttribute("pageList",pageList);
        model.addAttribute("currentPage",currentPage);
        return "index";
    }

    @GetMapping("/questionDTOPageNext")
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
        List<Integer> pageList = questionDTOPaginationDTO.getPageList();
        List<QuestionDTO> questionDTOs;
        questionDTOs = questionDTOPaginationDTO.getList();
        currentPage = questionDTOPaginationDTO.getCurrentPage();
        model.addAttribute("questionDTOs",questionDTOs);
        model.addAttribute("pageList",pageList);
        model.addAttribute("currentPage",currentPage);
        return "index";
    }


    @GetMapping("/questionGet")
    public String questionGet(@RequestParam(name="questionId")int questionId,Model model){
        QuestionDTO questionDTO;
        questionDTO = questionService.getQuestionDTOById(questionId);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
