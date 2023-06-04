package com.t4mako.bookmanagesystem.filter;

/**
 * @author T4mako
 * @date 2023/6/2 19:16
 */
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
 * 跨域问题过滤
 * */
@Component
/**/
public class CORSFilter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, GET");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        filterChain.doFilter(request,response);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
}

