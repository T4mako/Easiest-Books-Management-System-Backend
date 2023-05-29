package com.t4mako.bookmanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.bookmanagesystem.entity.Student;
import com.t4mako.bookmanagesystem.mapper.StudentMapper;
import com.t4mako.bookmanagesystem.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/5/28 10:59
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
