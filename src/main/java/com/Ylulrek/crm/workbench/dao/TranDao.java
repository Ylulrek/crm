package com.Ylulrek.crm.workbench.dao;

import com.Ylulrek.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran t);

    List<Tran> getTranByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int getTotal();

    List<Map<String, Object>> getCharts();

    Tran detail(String id);

    int changeStage(Tran t);
}
