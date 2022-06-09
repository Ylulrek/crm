package com.Ylulrek.crm.workbench.dao;

import com.Ylulrek.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity a);

    List<Activity> getActivityByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int drop(String[] ids);

    Activity getById(String id);

    int update(Activity a);

    Activity detail(String id);
}
