package com.Ylulrek.crm.workbench.service.impl;

import com.Ylulrek.crm.utils.SqlSessionUtil;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.dao.CustomerDao;
import com.Ylulrek.crm.workbench.domain.Clue;
import com.Ylulrek.crm.workbench.domain.Customer;
import com.Ylulrek.crm.workbench.service.CustomerService;

import java.util.List;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> sList=customerDao.getCustomerName(name);
        return sList;
    }

    @Override
    public PaginationVo<Customer> pageList(Map<String, Object> map) {
        int total = customerDao.getTotalByCondition(map);

        List<Customer> dataList = customerDao.getCustomerByCondition(map);

        PaginationVo<Customer> vo = new PaginationVo<Customer>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }
}
