package com.Ylulrek.crm.workbench.web.controller;

import com.Ylulrek.crm.settings.domain.User;
import com.Ylulrek.crm.settings.service.UserService;
import com.Ylulrek.crm.settings.service.impl.UserServiceImpl;
import com.Ylulrek.crm.utils.PrintJson;
import com.Ylulrek.crm.utils.ServiceFactory;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.Clue;
import com.Ylulrek.crm.workbench.domain.Contacts;
import com.Ylulrek.crm.workbench.domain.Customer;
import com.Ylulrek.crm.workbench.service.ClueService;
import com.Ylulrek.crm.workbench.service.ContactsService;
import com.Ylulrek.crm.workbench.service.CustomerService;
import com.Ylulrek.crm.workbench.service.impl.ClueServiceImpl;
import com.Ylulrek.crm.workbench.service.impl.ContactsServiceImpl;
import com.Ylulrek.crm.workbench.service.impl.CustomerServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入到线索控制器");
        String path = request.getServletPath();
        if ("/workbench/customer/pageList.do".equals(path)) {
            doPageList(request, response);
        }else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            //doGetCustomerName(request,response);
        }
    }

    private void doPageList(HttpServletRequest request, HttpServletResponse response) {
        String pageNoStr=request.getParameter("pageNo");
        String pageSizeStr=request.getParameter("pageSize");
        String name = request.getParameter("name");
        String owner=request.getParameter("owner");
        String phone=request.getParameter("phone");
        String website=request.getParameter("website");
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);
        map.put("name",name);
        map.put("owner",owner);
        map.put("phone",phone);
        map.put("website",website);
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        PaginationVo<Customer> vo=cs.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }


}