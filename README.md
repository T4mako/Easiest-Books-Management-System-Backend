# Easiest-Books-Management-System

最简单的图书管理系统-后端实现（SpringBoot+MybatisPlus）   
本项目为苏州科技大学JavaEE课程大作业     
This project is the final assignment for the JavaEE course at Suzhou University of Science and Technology（USTS）.      

## MySQL Tables

Book：id,ISBN,name,publisher,price,stock  
Student：id,name,password  
Lend：id,stuid,bookid,btime,rtime  
Admin：id,name,password  

## API

POST /student/login 学生登录  
POST /admin/login 管理员登录   
POST /register 学生注册   

管理员：
GET /admin/book/{str} 查询图书   
POST /admin/book 新建图书   
PUT /admin/book 修改图书  
DELETE /admin/book/{id} 删除图书   
GET /admin/book/list 查询所有图书   

学生： 
GET /student/book/{id} 查询图书   
GET /student/borrow/{id} 借阅图书   
GET /student/return/{id} 归还图书   
GET /student/lendList/ 查询借阅记录  
