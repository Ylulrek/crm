package com.Ylulrek.crm.workbench.service.impl;

import com.Ylulrek.crm.utils.SqlSessionUtil;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.dao.TranDao;
import com.Ylulrek.crm.workbench.dao.TranHistoryDao;
import com.Ylulrek.crm.workbench.domain.Customer;
import com.Ylulrek.crm.workbench.domain.Tran;
import com.Ylulrek.crm.workbench.domain.TranHistory;
import com.Ylulrek.crm.workbench.service.TranService;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {
    private TranDao tranDao= SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao= SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public PaginationVo<Tran> pageList(Map<String, Object> map) {
        int total = tranDao.getTotalByCondition(map);

        List<Tran> dataList = tranDao.getTranByCondition(map);

        PaginationVo<Tran> vo = new PaginationVo<Tran>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public Map<String, Object> getCharts() {
        //获得Total
        int total=tranDao.getTotal();
        //获得dataLiat
        List<Map<String,Object>> dataList=tranDao.getCharts();
        //将Total与dataList保存到Map中
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",total);
        map.put("dataList",dataList);
        return map;
    }
}
