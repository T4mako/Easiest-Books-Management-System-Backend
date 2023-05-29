package com.t4mako.bookmanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4mako.bookmanagesystem.entity.Book;
import com.t4mako.bookmanagesystem.mapper.BookMapper;
import com.t4mako.bookmanagesystem.service.BookService;
import org.springframework.stereotype.Service;

/**
 * @author T4mako
 * @date 2023/5/28 10:54
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper,Book> implements BookService {
}
