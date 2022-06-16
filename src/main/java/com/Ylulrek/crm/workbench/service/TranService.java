package com.Ylulrek.crm.workbench.service;

import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.Tran;

import java.util.Map;

public interface TranService {
    PaginationVo<Tran> pageList(Map<String, Object> map);

    Map<String, Object> getCharts();

}
