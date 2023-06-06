package com.t4mako.bookmanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sun.xml.internal.fastinfoset.util.LocalNameQualifiedNamesMap;
import com.t4mako.bookmanagesystem.entity.Book;
import com.t4mako.bookmanagesystem.entity.Lend;
import com.t4mako.bookmanagesystem.entity.Student;
import com.t4mako.bookmanagesystem.service.BookService;
import com.t4mako.bookmanagesystem.service.LendService;
import com.t4mako.bookmanagesystem.service.StudentService;
import com.t4mako.bookmanagesystem.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/**
 * @author T4mako
 * @date 2023/5/28 16:56
 */
@Slf4j //log
@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private BookService bookService;
    @Autowired
    private LendService lendService;


    /**
     * @Author T4mako
     * @Description //TODO 学生登录
     * @Date 16:57 2023/5/28
     */
    @PostMapping("/login")
    public String login(@RequestBody Student student,HttpServletRequest request){
        String token = request.getHeader("token");
        String str = null;
        String name = null;
        String password = null;
        if(token != null){
            // token不为空，解析token对应用户是否与登录用户相同
            try {
                str = String.valueOf(JwtUtils.parseToken(token));
                name = JwtUtils.extractValue(str,"sub");
                password = JwtUtils.extractValue(str,"password");
                if(name.equals(student.getName()) && password.equals(student.getPassword())){
                    return "true";
                }
            }catch (Exception e){
                // 无法解析Token
            }
        }
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        // 查询同名的学生
        queryWrapper.eq(Student::getName,student.getName());
        Student one = studentService.getOne(queryWrapper);
        if(one == null || !one.getPassword().equals(student.getPassword())){
            // 登录失败
            return "false";
        }
        // 返回生成 token
        return JwtUtils.generateToken(one.getName(),one.getPassword());
    }

    /**
     * @Author T4mako
     * @Description //TODO 学生注册
     * @Date 22:52 2023/5/28
     */
    @PostMapping("/register")
    @Transactional
    public boolean register(@RequestBody Student student){
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getName,student.getName());
        if(studentService.getOne(queryWrapper) != null){
            return false;
        }
        return studentService.save(student);

    }

    /**
     * @Author T4mako
     * @Description //TODO 查询图书
     * @Date 22:54 2023/5/28
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
     * @Description //TODO 借阅图书
     * @Date 22:59 2023/5/28
     */
    @GetMapping("/borrow/{id}")
    @Transactional
    public boolean borrow(@PathVariable Integer id,HttpServletRequest request){
        Student student = null;
        String token = request.getHeader("token");
        // 取出用户名，并取出用户
        if(token != null){
            String str = String.valueOf(JwtUtils.parseToken(token));
            String name = JwtUtils.extractValue(str, "sub");
            LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Student::getName,name);
            student = studentService.getOne(queryWrapper);
        }
        log.info(token);
        Book book = bookService.getById(id);
        // 库存量等于0，返回false
        if(book.getStock() <= 0){
            return false;
        }
        LambdaUpdateWrapper<Book> updateWrapper = new LambdaUpdateWrapper<>();
        // 修改图书库存量-1
        updateWrapper.eq(Book::getId,id).set(Book::getStock,book.getStock()-1);
        bookService.update(updateWrapper);

        // 创建lend对象
        Lend lend = new Lend();
        lend.setId(UUID.randomUUID().toString());
        lend.setBookid(book.getId());
        lend.setBtime(LocalDateTime.now());
        lend.setBookName(book.getName());

        // 通过
        lend.setStuid(student.getId());
        // 插入lend记录
        lendService.save(lend);
        return true;
    }

    /**
     * @Author T4mako
     * @Description //TODO 归还图书
     * @Date 23:06 2023/5/28
     */
    @GetMapping("/return/{id}")
    public boolean returnBook(@PathVariable String id){ //传入 lend id
        Lend lend = lendService.getById(id);
        // 修改库存
        bookService.update(new LambdaUpdateWrapper<Book>()
                .eq(Book::getId,lend.getBookid())
                .set(Book::getStock,bookService.getById(lend.getBookid()).getStock()+1));

        // 设置归还时间
        lend.setRtime(LocalDateTime.now());
        lendService.updateById(lend);
        return true;
    }

    /**
     * @Author T4mako
     * @Description //TODO 查看借阅记录
     * @Date 14:46 2023/5/29
     */
    @GetMapping("/borrowList")
    public List<Lend> borrowList(HttpServletRequest request){
        // 获取token
        String str = String.valueOf(JwtUtils.parseToken(request.getHeader("token")));

        // token获取用户名
        String name = JwtUtils.extractValue(str, "sub");
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getName,name);

        // 查询用户id
        Student one = studentService.getOne(queryWrapper);
        Integer stuId = one.getId();

        // 查询用户借阅
        LambdaQueryWrapper<Lend> lendQueryWrapper = new LambdaQueryWrapper<>();
        lendQueryWrapper.eq(Lend::getStuid,stuId).orderByDesc(Lend::getBtime);
        return lendService.list(lendQueryWrapper);
    }

    /**
     * @Author T4mako
     * @Description //TODO 查询所有图书
     * @Date 16:45 2023/6/2
     */
    @GetMapping("/book/list")
    public List<Book> allBook(){
        return bookService.list();
    }

}
