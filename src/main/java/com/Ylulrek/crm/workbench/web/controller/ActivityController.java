package com.Ylulrek.crm.workbench.web.controller;

import com.Ylulrek.crm.settings.domain.User;
import com.Ylulrek.crm.settings.service.UserService;
import com.Ylulrek.crm.settings.service.impl.UserServiceImpl;
import com.Ylulrek.crm.utils.*;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.Activity;
import com.Ylulrek.crm.workbench.domain.ActivityRemark;
import com.Ylulrek.crm.workbench.service.ActivityService;
import com.Ylulrek.crm.workbench.service.impl.ActivityServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入到市场活动控制器");
        String path = request.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)) {
            doGetUserList(request, response);
        } else if ("/workbench/activity/save.do".equals(path)) {
            dosave(request,response);
        } else if ("/workbench/activity/pageList.do".equals(path)) {
            doPageList(request,response);
        }else if ("/workbench/activity/drop.do".equals(path)) {
            doDrop(request,response);
        }else if ("/workbench/activity/getUserAndActivity.do".equals(path)) {
            dogetUserAndActivity(request,response);
        }else if ("/workbench/activity/update.do".equals(path)) {
            doUpdate(request,response);
        }else if ("/workbench/activity/detail.do".equals(path)) {
            doDetail(request,response);
        }else if ("/workbench/activity/getRemarkListById.do".equals(path)) {
            dogetRemarkListById(request,response);
        }else if ("/workbench/activity/deleteRemark.do".equals(path)) {
            doDeleteRemark(request,response);
        }else if ("/workbench/activity/saveRemark.do".equals(path)) {
            doSaveRemark(request,response);
        }else if ("/workbench/activity/updateRemark.do".equals(path)) {
            doUpdateRemark(request,response);
        }
    }

    private void doUpdateRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime= DateTimeUtil.getSysTime();
        String editeBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="1";
        ActivityRemark ar=new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditTime(editTime);
        ar.setEditBy(editeBy);
        ar.setEditFlag(editFlag);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag=as.updateRemark(ar);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("success",flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);

    }

    private void doSaveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到新增备注操作");
        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");
        String id=UUIDUtil.getUUID();
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="0";
        ActivityRemark ar=new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);
        ar.setActivityId(activityId);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.saveRemark(ar);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("success",flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);

    }

    private void doDeleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到删除备注操作");
        String id = request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);

    }

    private void dogetRemarkListById(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据市场活动id，取得备注信息列表");
        String activityId=request.getParameter("activityId");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> arList=as.getRemarkListById(activityId);
        PrintJson.printJsonObj(response,arList);

    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到跳转到详细信息页的操作");
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a=as.detail(id);
        request.setAttribute("a",a);

        //转发到detail.jsp页面
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void doUpdate(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动修改操作");
        String id=request.getParameter("id");
        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String cost=request.getParameter("cost");
        String description=request.getParameter("description");
        //修改时间：当前系统时间
        String editTime= DateTimeUtil.getSysTime();
        //修改人：
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        Activity a=new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setEditTime(editTime);
        a.setEditBy(editBy);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.update(a);
        PrintJson.printJsonFlag(response,flag);
    }

    private void dogetUserAndActivity(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String,Object> map= as.getUserAndActivity(id);
        PrintJson.printJsonObj(response,map);
    }

    private void doDrop(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动删除操作");
        String[] ids = request.getParameterValues("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flags=as.drop(ids);
        PrintJson.printJsonFlag(response,flags);
    }

    private void doPageList(HttpServletRequest request, HttpServletResponse response) {

        String pageNoStr=request.getParameter("pageNo");
        String pageSizeStr=request.getParameter("pageSize");
        String name=request.getParameter("name");
        String owner=request.getParameter("owner");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVo<Activity> vo= as.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }

    private void dosave(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的添加操作");
        String id= UUIDUtil.getUUID();
        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String cost=request.getParameter("cost");
        String description=request.getParameter("description");
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();

        Activity a=new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);


        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Boolean flag=as.save(a);
        PrintJson.printJsonFlag(response,flag);
    }

    private void doGetUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("获取用户信息列表");
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList=us.getUserList();
        PrintJson.printJsonObj(response,uList);
    }
}



