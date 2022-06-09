package com.Ylulrek.crm.workbench.web.controller;

import com.Ylulrek.crm.settings.domain.User;
import com.Ylulrek.crm.settings.service.UserService;
import com.Ylulrek.crm.settings.service.impl.UserServiceImpl;
import com.Ylulrek.crm.utils.DateTimeUtil;
import com.Ylulrek.crm.utils.PrintJson;
import com.Ylulrek.crm.utils.ServiceFactory;
import com.Ylulrek.crm.utils.UUIDUtil;
import com.Ylulrek.crm.workbench.domain.Clue;
import com.Ylulrek.crm.workbench.service.ClueService;
import com.Ylulrek.crm.workbench.service.impl.ClueServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入到线索控制器");
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path)) {
            doGetUserList(request, response);
        } else if ("/workbench/clue/save.do".equals(path)) {
            doSave(request,response);
        }
    }

    private void doSave(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到添加线索操作");
        String id= UUIDUtil.getUUID();
        String fullname=request.getParameter("fullname");
        String appellation=request.getParameter("appellation");
        String owner=request.getParameter("owner");
        String company =request.getParameter("company");
        String job=request.getParameter("job");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String website=request.getParameter("website");
        String mphone=request.getParameter("mphone");
        String state=request.getParameter("state");
        String source=request.getParameter("source");
        String description=request.getParameter("description");
        String contactSummary=request.getParameter("contactSummary");
        String nextContactTime=request.getParameter("nextContactTime");
        String address=request.getParameter("address");
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();

        Clue clue=new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);
        clue.setCreateTime(createTime);
        clue.setCreateBy(createBy);

        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=cs.save(clue);
        PrintJson.printJsonFlag(response,flag);
    }

    private void doGetUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList=us.getUserList();
        PrintJson.printJsonObj(response,uList);
    }
}