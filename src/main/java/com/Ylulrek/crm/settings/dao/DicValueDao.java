package com.Ylulrek.crm.settings.dao;

import com.Ylulrek.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
