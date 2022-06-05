package com.Ylulrek.crm.workbench.service.impl;

import com.Ylulrek.crm.utils.SqlSessionUtil;
import com.Ylulrek.crm.workbench.dao.ActivityDao;
import com.Ylulrek.crm.workbench.domain.Activity;
import com.Ylulrek.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public Boolean save(Activity a) {

        boolean flag=true;
        int nums=activityDao.save(a);
        if(nums!=1){
            flag=false;
        }
        return flag;
    }
}
