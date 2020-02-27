package com.hjh.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjh.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
