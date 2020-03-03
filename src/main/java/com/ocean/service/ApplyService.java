package com.ocean.service;

import java.util.HashMap;
import java.util.List;
import com.ocean.entity.Apply;
import com.github.pagehelper.PageInfo;


public interface ApplyService {

    Apply getApply(Integer id);

    void save(Apply model);

    void update(Apply model);

    void del(Integer cid, Integer sid);

    int getCount();

    PageInfo<Apply> query(int currentPage, int itemsPerPage, HashMap<String, Object> paramMap);
    int queryCount(HashMap<String, Object> paramMap);

}
