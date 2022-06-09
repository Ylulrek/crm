package com.Ylulrek.crm.workbench.dao;

import com.Ylulrek.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {


    int getCountByAids(String[] ids);

    int dropByAids(String[] ids);

    List<ActivityRemark> getRemarkListById(String activityId);

    int deleteById(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}
