package com.Ylulrek.crm.workbench.web.controller;

import com.Ylulrek.crm.settings.domain.User;
import com.Ylulrek.crm.settings.service.UserService;
import com.Ylulrek.crm.settings.service.impl.UserServiceImpl;
import com.Ylulrek.crm.utils.DateTimeUtil;
import com.Ylulrek.crm.utils.PrintJson;
import com.Ylulrek.crm.utils.ServiceFactory;
import com.Ylulrek.crm.utils.UUIDUtil;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.*;
import com.Ylulrek.crm.workbench.service.*;
import com.Ylulrek.crm.workbench.service.impl.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入到线索控制器");
        String path = request.getServletPath();
        if ("/workbench/transaction/add.do".equals(path)) {
            doAdd(request, response);
        }else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            doGetCustomerName(request,response);
        }else if ("/workbench/transaction/pageList.do".equals(path)) {
            doPageList(request,response);
        }else if ("/workbench/transaction/getCharts.do".equals(path)) {
            doGetCharts(request,response);
        }else if ("/workbench/transaction/getActivityListByName.do".equals(path)) {
            doGetActivityListByName(request,response);
        }else if ("/workbench/transaction/getContactsListByName.do".equals(path)) {
            doGetContactsListByName(request,response);
        }else if ("/workbench/transaction/save.do".equals(path)) {
            doSave(request,response);
        }else if ("/workbench/transaction/detail.do".equals(path)) {
            doDetail(request,response);
        }else if ("/workbench/transaction/getHistoryListByTranId.do".equals(path)) {
            doGetHistoryListByTranId(request,response);
        }else if ("/workbench/transaction/changeStage.do".equals(path)) {
            doChangeStage(request,response);
        }
    }

    private void doChangeStage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行改变阶段的操作");
        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editTime= DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();

        Tran t=new Tran();
        t.setId(id);
        t.setMoney(money);
        t.setStage(stage);
        t.setEditTime(editTime);
        t.setEditBy(editBy);
        t.setExpectedDate(expectedDate);

        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag=ts.changeStage(t);

        Map<String,String> pMap= (Map<String, String>) this.getServletContext().getAttribute("pMap");
        t.setPossibility(pMap.get(stage));

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("success",flag);
        map.put("t",t);

        PrintJson.printJsonObj(response,map);
    }

    private void doGetHistoryListByTranId(HttpServletRequest request, HttpServletResponse response) {

        String tranId = request.getParameter("tranId");
        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<TranHistory> thList =ts.getHistoryListByTranId(tranId);
        //阶段和可能性之间的对应关系
        Map<String,String> pMap= (Map<String, String>) this.getServletContext().getAttribute("pMap");
        for (TranHistory th:thList){
            String stage=th.getStage();
            th.setPossibility(pMap.get(stage));
        }
        PrintJson.printJsonObj(response,thList);
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("跳转到详细信息页");
        String id=request.getParameter("id");
        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());
        Tran t=ts.detail(id);
        //处理可能性
        String stage=t.getStage();
        Map<String,String> pMap= (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String possibility=pMap.get(stage);

        request.setAttribute("t",t);
        request.setAttribute("possibility",possibility);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);
    }

    private void doSave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("执行添加交易的操作");
        String id=UUIDUtil.getUUID();
        String owner=request.getParameter("owner");
        String money=request.getParameter("money");
        String name=request.getParameter("name");
        String expectedDate=request.getParameter("expectedDate");
        String customerName=request.getParameter("customerName");
        String stage=request.getParameter("stage");
        String type=request.getParameter("type");
        String source =request.getParameter("source");
        String activityId=request.getParameter("activityId");
        String contactsId=request.getParameter("contactsId");
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String description=request.getParameter("description");
        String contactSummary=request.getParameter("contactSummary");
        String nextContactTime=request.getParameter("nextContactTime");
        Tran t=new Tran();
        t.setId(id);
        t.setOwner(owner);
        t.setMoney(money);
        t.setName(name);
        t.setExpectedDate(expectedDate);
        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateTime(createTime);
        t.setCreateBy(createBy);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);
        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag=ts.save(t,customerName);
        if(flag){
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }
    }

    private void doGetContactsListByName(HttpServletRequest request, HttpServletResponse response) {
        String cname = request.getParameter("cname");
        ContactsService cs= (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());
        List<Contacts> cList=cs.getContactsListByName(cname);
        PrintJson.printJsonObj(response,cList);
    }

    private void doGetActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        String aname = request.getParameter("aname");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList=as.getActivityListByName(aname);
        PrintJson.printJsonObj(response,aList);
    }

    private void doGetCharts(HttpServletRequest request, HttpServletResponse response) {

        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());

        Map<String,Object> map=ts.getCharts();
        PrintJson.printJsonObj(response,map);
    }

    private void doPageList(HttpServletRequest request, HttpServletResponse response) {
        String pageNoStr=request.getParameter("pageNo");
        String pageSizeStr=request.getParameter("pageSize");
        String owner=request.getParameter("owner");
        String name = request.getParameter("name");
        String customerName = request.getParameter("customerName");
        String stage=request.getParameter("stage");
        String type=request.getParameter("type");
        String source=request.getParameter("source");
        String contactsName=request.getParameter("contactsName");
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);
        map.put("owner",owner);
        map.put("name",name);
        map.put("customerName",customerName);
        map.put("stage",stage);
        map.put("type",type);
        map.put("source",source);
        map.put("contactsName",contactsName);
        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());
        PaginationVo<Tran> vo=ts.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }

    private void doGetCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得客户名称列表");
        String name = request.getParameter("name");
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<String> sList=cs.getCustomerName(name);
        PrintJson.printJsonObj(response,sList);
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("进入到跳转到交易添加页的操作");
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList=us.getUserList();
        request.setAttribute("uList",uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);

    }

}