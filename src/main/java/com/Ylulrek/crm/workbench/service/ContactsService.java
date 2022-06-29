package com.Ylulrek.crm.workbench.service;

import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.Contacts;
import com.Ylulrek.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface ContactsService {

    PaginationVo<Contacts> pageList(Map<String, Object> map);

    List<Contacts> getContactsListByName(String cname);
}
