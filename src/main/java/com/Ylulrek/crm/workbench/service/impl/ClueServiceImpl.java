package com.Ylulrek.crm.workbench.service.impl;

import com.Ylulrek.crm.utils.SqlSessionUtil;
import com.Ylulrek.crm.utils.UUIDUtil;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.dao.ClueActivityRelationDao;
import com.Ylulrek.crm.workbench.dao.ClueDao;
import com.Ylulrek.crm.workbench.domain.Activity;
import com.Ylulrek.crm.workbench.domain.Clue;
import com.Ylulrek.crm.workbench.domain.ClueActivityRelation;
import com.Ylulrek.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao  clueActivityRelationDao=SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    @Override
    public boolean save(Clue clue) {
        boolean flag=true;
        int num=clueDao.save(clue);
        if(num!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Clue> pageList(Map<String, Object> map) {
        int total=clueDao.getTotalByCondition(map);

        List<Clue> dataList=clueDao.getClueByCondition(map);

        PaginationVo<Clue> vo=new PaginationVo<Clue>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public Clue detail(String id) {
        Clue clue =clueDao.detail(id);

        return clue;
    }

    @Override
    public boolean unbund(String id) {
        boolean flag=true;
        int num=clueActivityRelationDao.unbund(id);
        if (num!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean bund(String clueId, String[] activityIds) {
        boolean flag=true;
        ClueActivityRelation car=new ClueActivityRelation();
        car.setClueId(clueId);
        for (String aid:activityIds){
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            int num=clueActivityRelationDao.bund(car);
            if(num!=1){
                flag=false;
            }
        }
        return flag;
    }
}
