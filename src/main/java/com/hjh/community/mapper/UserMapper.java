package com.hjh.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjh.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
