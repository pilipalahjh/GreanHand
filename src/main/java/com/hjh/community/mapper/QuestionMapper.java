package com.hjh.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjh.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    //更新阅读数+1
    @Update("update question set view_count = view_count+1 where id = ${id}")
    int incView(int id);
}
