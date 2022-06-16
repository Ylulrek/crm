package com.Ylulrek.crm.workbench.web.controller;

import com.Ylulrek.crm.settings.domain.User;
import com.Ylulrek.crm.settings.service.UserService;
import com.Ylulrek.crm.settings.service.impl.UserServiceImpl;
import com.Ylulrek.crm.utils.DateTimeUtil;
import com.Ylulrek.crm.utils.PrintJson;
import com.Ylulrek.crm.utils.ServiceFactory;
import com.Ylulrek.crm.utils.UUIDUtil;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.Activity;
import com.Ylulrek.crm.workbench.domain.Clue;
import com.Ylulrek.crm.workbench.domain.Tran;
import com.Ylulrek.crm.workbench.service.ActivityService;
import com.Ylulrek.crm.workbench.service.ClueService;
import com.Ylulrek.crm.workbench.service.impl.ActivityServiceImpl;
import com.Ylulrek.crm.workbench.service.impl.ClueServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入到线索控制器");
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path)) {
            doGetUserList(request, response);
        }else if ("/workbench/clue/save.do".equals(path)) {
            doSave(request,response);
        }else if ("/workbench/clue/pageList.do".equals(path)) {
            doPageList(request,response);
        }else if ("/workbench/clue/detail.do".equals(path)) {
            doDetail(request,response);
        }else if ("/workbench/clue/getActivityByClueId.do".equals(path)) {
            doGetActivityByClueId(request,response);
        }else if ("/workbench/clue/unbund.do".equals(path)) {
            doUnbund(request,response);
        }else if ("/workbench/clue/getActivityListByNameNotByClueId.do".equals(path)) {
            doGetActivityListByNameNotByClueId(request,response);
        }else if ("/workbench/clue/bund.do".equals(path)) {
            doBund(request,response);
        }else if ("/workbench/clue/getActivityListByName.do".equals(path)) {
            doGetActivityListByName(request,response);
        }else if ("/workbench/clue/convert.do".equals(path)) {
            doConvert(request,response);
        }
    }

    private void doConvert(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        System.out.println("执行线索转换的操作");
        String clueId = request.getParameter("clueId");
        //接收是否需要创建交易的标记
        String flag = request.getParameter("flag");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        Tran t = null;
        //如果需要创建交易
        if("a".equals(flag)){
            t = new Tran();
            //接收交易表单中的参数
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            t.setId(id);
            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
        }
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag1 = cs.convert(clueId,t,createBy);

        if(flag1){
            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
        }


    }

    private void doGetActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        String aname = request.getParameter("aname");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList=as.getActivityListByName(aname);
        PrintJson.printJsonObj(response,aList);
    }

    private void doBund(HttpServletRequest request, HttpServletResponse response) {
        String clueId = request.getParameter("clueId");
        String[] activityIds = request.getParameterValues("activityId");
        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=cs.bund(clueId,activityIds);
        PrintJson.printJsonFlag(response,flag);
    }

    private void doGetActivityListByNameNotByClueId(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String clueId = request.getParameter("clueId");
        Map<String,String> map=new HashMap<String,String>();
        map.put("aname",name);
        map.put("clueId",clueId);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList=as.getActivityListByNameNotByClueId(map);
        PrintJson.printJsonObj(response,aList);
    }

    private void doUnbund(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=cs.unbund(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void doGetActivityByClueId(HttpServletRequest request, HttpServletResponse response) {
        String clueId = request.getParameter("clueId");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList=as.getActivityByClueId(clueId);
        PrintJson.printJsonObj(response,aList);
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String id = request.getParameter("id");

        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue=cs.detail(id);
        request.setAttribute("clue",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    private void doPageList(HttpServletRequest request, HttpServletResponse response) {
        String pageNoStr=request.getParameter("pageNo");
        String pageSizeStr=request.getParameter("pageSize");
        String fullname = request.getParameter("fullname");
        String company=request.getParameter("company");
        String phone=request.getParameter("phone");
        String mphone=request.getParameter("mphone");
        String source=request.getParameter("source");
        String owner=request.getParameter("owner");
        String state=request.getParameter("state");
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone);
        map.put("mphone",mphone);
        map.put("source",source);
        map.put("owner",owner);
        map.put("state",state);
        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        PaginationVo<Clue> vo=cs.pageList(map);
        PrintJson.printJsonObj(response,vo);

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