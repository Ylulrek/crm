package com.Ylulrek.crm.settings.web.controller;

import com.Ylulrek.crm.settings.domain.User;
import com.Ylulrek.crm.settings.service.UserService;
import com.Ylulrek.crm.settings.service.impl.UserServiceImpl;
import com.Ylulrek.crm.utils.MD5Util;
import com.Ylulrek.crm.utils.PrintJson;
import com.Ylulrek.crm.utils.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path=request.getServletPath();
        if ("/settings/user/login.do".equals(path)){
            doLogin(request,response);
        }else if ("/settings/user/***".equals(path)){

        }
    }
    private void doLogin(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到验证登录操作");

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //将密码转换为密文形式
        loginPwd= MD5Util.getMD5(loginPwd);
        //接收ip地址
        String ip=request.getRemoteAddr();
        System.out.println(ip);
        //统一使用代理类形态的接口对象
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try{
            User user=us.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            //执行到此处表示登录成功
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            String msg=e.getMessage();

            Map<String,Object> map=new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map)



















































































































































            ;

        }

    }

}
