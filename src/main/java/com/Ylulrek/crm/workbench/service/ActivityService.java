package com.Ylulrek.crm.workbench.service;

import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.Activity;
import com.Ylulrek.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Boolean save(Activity a);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean drop(String[] ids);

    Map<String, Object> getUserAndActivity(String id);

    boolean update(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListById(String activityId);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);
}
