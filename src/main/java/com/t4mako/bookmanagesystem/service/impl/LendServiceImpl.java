package com.t4mako.bookmanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.bookmanagesystem.entity.Lend;
import com.t4mako.bookmanagesystem.mapper.LendMapper;
import com.t4mako.bookmanagesystem.service.LendService;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/5/28 10:57
 */
@Service
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService {
}
