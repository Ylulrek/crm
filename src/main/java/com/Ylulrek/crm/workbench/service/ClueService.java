package com.Ylulrek.crm.workbench.service;

import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.domain.Clue;
import com.Ylulrek.crm.workbench.domain.Tran;

import java.util.Map;

public interface ClueService {
    boolean save(Clue clue);

    PaginationVo<Clue> pageList(Map<String, Object> map);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String clueId, String[] activityIds);

    boolean convert(String clueId, Tran t, String createBy);
}
