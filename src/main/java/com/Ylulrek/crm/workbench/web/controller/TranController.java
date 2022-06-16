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
import com.Ylulrek.crm.workbench.domain.Contacts;
import com.Ylulrek.crm.workbench.domain.Tran;
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
        }
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
        String customerId = request.getParameter("customername");
        String stage=request.getParameter("stage");
        String type=request.getParameter("type");
        String source=request.getParameter("source");
        String contactsId=request.getParameter("contactsname");
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);
        map.put("owner",owner);
        map.put("name",name);
        map.put("customerId",customerId);
        map.put("stage",stage);
        map.put("type",type);
        map.put("source",source);
        map.put("contactsId",contactsId);
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