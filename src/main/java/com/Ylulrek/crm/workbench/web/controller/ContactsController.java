package com.Ylulrek.crm.workbench.web.controller;

import com.Ylulrek.crm.utils.PrintJson;
import com.Ylulrek.crm.utils.ServiceFactory;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.Contacts;
import com.Ylulrek.crm.workbench.domain.Customer;
import com.Ylulrek.crm.workbench.service.ContactsService;
import com.Ylulrek.crm.workbench.service.CustomerService;
import com.Ylulrek.crm.workbench.service.impl.ContactsServiceImpl;
import com.Ylulrek.crm.workbench.service.impl.CustomerServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ContactsController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入到线索控制器");
        String path = request.getServletPath();
        if ("/workbench/contacts/pageList.do".equals(path)) {
            doPageList(request, response);
        }else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            //doGetCustomerName(request,response);
        }
    }

    private void doPageList(HttpServletRequest request, HttpServletResponse response) {
        String pageNoStr=request.getParameter("pageNo");
        String pageSizeStr=request.getParameter("pageSize");
        String owner=request.getParameter("owner");
        String fullname = request.getParameter("fullname");
        String name = request.getParameter("name");
        String source=request.getParameter("source");
        String brith=request.getParameter("brith");
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);
        map.put("owner",owner);
        map.put("fullname",fullname);
        map.put("name",name);
        map.put("source",source);
        map.put("brith",brith);
        ContactsService cs= (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());
        PaginationVo<Contacts> vo=cs.pageList(map);
        PrintJson.printJsonObj(response,vo);

    }
}