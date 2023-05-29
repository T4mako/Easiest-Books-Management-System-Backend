package com.t4mako.bookmanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.bookmanagesystem.entity.Admin;
import com.t4mako.bookmanagesystem.mapper.AdminMapper;
import com.t4mako.bookmanagesystem.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/5/28 11:09
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
