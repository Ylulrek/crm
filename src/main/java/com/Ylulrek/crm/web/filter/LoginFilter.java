package com.Ylulrek.crm.web.filter;

import com.Ylulrek.crm.settings.domain.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("进入到登录验证的过滤器");
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;

        String path=request.getServletPath();
        if("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            chain.doFilter(req,resp);
        }else {
            HttpSession session=request.getSession();
            User user= (User) session.getAttribute("user");
            if(user!=null){
                chain.doFilter(req,resp);
            }else {
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }
    }
}
