package com.t4mako.bookmanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.t4mako.bookmanagesystem.entity.Admin;
import com.t4mako.bookmanagesystem.entity.Book;
import com.t4mako.bookmanagesystem.service.AdminService;
import com.t4mako.bookmanagesystem.service.BookService;
import lombok.Lombok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author T4mako
 * @date 2023/5/28 11:08
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private BookService bookService;

    /**
     * @Author T4mako
     * @Description //TODO admin登录
     * @Date 11:24 2023/5/28
     */
    @PostMapping  ("/login")
    public boolean login(@RequestBody Admin admin){
        // 获取用户名和密码
        String name = admin.getName();
        String password = admin.getPassword();
        // 根据名查询用户
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getName,name);
        Admin one = adminService.getOne(queryWrapper);
        // 若查询为空，返回false
        if(one == null){
            return false;
        }
        // 比较密码
        return one.getPassword().equals(password);
    }

    /**
     * @Author T4mako
     * @Description //TODO 新建图书
     * @Date 17:21 2023/5/28
     */
    @PostMapping("/book")
    public boolean addBook(@RequestBody Book book){
        // 如果图书的ISBN在数据库中有，添加库存
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getIsbn,book.getIsbn());
        // 获取图书实例
        Book one = bookService.getOne(queryWrapper);
        if(one != null){
            LambdaUpdateWrapper<Book> updateWrapper = new LambdaUpdateWrapper<>();
            // 更新库存
            updateWrapper.eq(Book::getIsbn,book.getIsbn()).set(Book::getStock,one.getStock() + book.getStock());
            bookService.update(updateWrapper);
            return true;
        }
        // 如果图书的ISBN在数据库中没有，直接添加
        bookService.save(book);
        return true;
    }

    /**
     * @Author T4mako
     * @Description //TODO 查询所有图书
     * @Date 17:24 2023/5/28
     */
    @GetMapping("/book/list")
    public List<Book> allBook(){
        return bookService.list();
    }

    /**
     * @Author T4mako
     * @Description //TODO 条件查询图书
     * @Date 17:33 2023/5/28
     */
    @GetMapping("/book/{str}")
    public List<Book> searchBook(@PathVariable("str") String str){
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        // 根据传过来的的值匹配 ISBN 和 name
        queryWrapper.or(i -> i.like(Book::getName, str))
                .or(i -> i.like(Book::getIsbn, str));
        return bookService.list(queryWrapper);
    }

    /**
     * @Author T4mako
     * @Description //TODO 修改图书
     * @Date 22:30 2023/5/28
     */
    @PutMapping("/book")
    public void update(@RequestBody Book book){
        LambdaUpdateWrapper<Book> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Book::getId,book.getId());
        bookService.update(book,updateWrapper);
    }

    /**
     * @Author T4mako
     * @Description //TODO 删除图书
     * @Date 22:34 2023/5/28
     */
    @DeleteMapping("/book")
    public boolean delete(@RequestBody Book book){
        bookService.removeById(book.getId());
        return true;
    }
}
