package com.Ylulrek.crm.workbench.dao;

import com.Ylulrek.crm.workbench.domain.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsDao {

    int save(Contacts con);

    List<Contacts> getContactsByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);
}
