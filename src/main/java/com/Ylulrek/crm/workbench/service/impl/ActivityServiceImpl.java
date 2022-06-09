package com.Ylulrek.crm.workbench.service.impl;

import com.Ylulrek.crm.settings.dao.UserDao;
import com.Ylulrek.crm.settings.domain.User;
import com.Ylulrek.crm.utils.SqlSessionUtil;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.dao.ActivityDao;
import com.Ylulrek.crm.workbench.dao.ActivityRemarkDao;
import com.Ylulrek.crm.workbench.domain.Activity;
import com.Ylulrek.crm.workbench.domain.ActivityRemark;
import com.Ylulrek.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public Boolean save(Activity a) {
        boolean flag=true;
        int nums=activityDao.save(a);
        if(nums!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {

        int total=activityDao.getTotalByCondition(map);

        List<Activity> dataList=activityDao.getActivityByCondition(map);

        PaginationVo<Activity> vo=new PaginationVo<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean drop(String[] ids) {

        boolean flag=true;
        //查询出需要删除的备注数量
        int num1=activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受影响的条数（实际删除数量）
        int num2=activityRemarkDao.dropByAids(ids);
        if(num1!=num2){
            flag=false;
        }
        //删除市场活动
        int num3=activityDao.drop(ids);

        if(num3!=ids.length){
            flag=false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserAndActivity(String id) {
        //取uList
        List<User> uList=userDao.getUserList();
        //取activity
        Activity a=activityDao.getById(id);
        //打包
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("uList",uList);
        map.put("a",a);

        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag=true;
        int nums=activityDao.update(a);
        if(nums!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity a=activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListById(String activityId) {

        List<ActivityRemark> arList=activityRemarkDao.getRemarkListById(activityId);

        return arList;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag=true;
        int num=activityRemarkDao.deleteById(id);
        if(num!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag=true;
        int num=activityRemarkDao.saveRemark(ar);
        if(num!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag=true;
        int num=activityRemarkDao.updateRemark(ar);
        if(num!=1){
            flag=false;
        }
        return flag;
    }
}
