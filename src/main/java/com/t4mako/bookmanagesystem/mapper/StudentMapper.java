package com.t4mako.bookmanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4mako.bookmanagesystem.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author T4mako
 * @date 2023/5/28 10:57
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
