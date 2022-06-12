package com.Ylulrek.crm.workbench.dao;


import com.Ylulrek.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue clue);

    int getTotalByCondition(Map<String, Object> map);

    List<Clue> getClueByCondition(Map<String, Object> map);

    Clue detail(String id);
}
