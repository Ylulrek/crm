package com.Ylulrek.crm.workbench.service.impl;

import com.Ylulrek.crm.utils.SqlSessionUtil;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.dao.ContactsDao;
import com.Ylulrek.crm.workbench.dao.CustomerDao;
import com.Ylulrek.crm.workbench.domain.Activity;
import com.Ylulrek.crm.workbench.domain.Contacts;
import com.Ylulrek.crm.workbench.domain.Customer;
import com.Ylulrek.crm.workbench.service.ContactsService;
import com.Ylulrek.crm.workbench.service.CustomerService;

import java.util.List;
import java.util.Map;

public class ContactsServiceImpl implements ContactsService {
    private ContactsDao contactsDao= SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);

    @Override
    public PaginationVo<Contacts> pageList(Map<String, Object> map) {
        int total = contactsDao.getTotalByCondition(map);

        List<Contacts> dataList = contactsDao.getContactsByCondition(map);

        PaginationVo<Contacts> vo = new PaginationVo<Contacts>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public List<Contacts> getContactsListByName(String cname) {
        List<Contacts> cList=contactsDao.getContactsListByName(cname);
        return cList;
    }
}
