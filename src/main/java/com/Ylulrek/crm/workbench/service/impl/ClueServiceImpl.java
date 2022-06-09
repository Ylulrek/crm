package com.Ylulrek.crm.workbench.service.impl;

import com.Ylulrek.crm.utils.SqlSessionUtil;
import com.Ylulrek.crm.workbench.dao.ClueDao;
import com.Ylulrek.crm.workbench.domain.Clue;
import com.Ylulrek.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);


    @Override
    public boolean save(Clue clue) {
        boolean flag=true;
        int num=clueDao.save(clue);
        if(num!=1){
            flag=false;
        }
        return flag;
    }
}
