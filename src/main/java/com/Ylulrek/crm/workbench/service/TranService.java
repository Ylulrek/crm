package com.Ylulrek.crm.workbench.service;

import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.Tran;
import com.Ylulrek.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    PaginationVo<Tran> pageList(Map<String, Object> map);

    Map<String, Object> getCharts();

    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean changeStage(Tran t);
}
