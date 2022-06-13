package com.Ylulrek.crm.workbench.dao;

import com.Ylulrek.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer cus);
}
