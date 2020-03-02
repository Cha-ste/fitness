package com.ocean.mapper;

import com.ocean.entity.Apply;
import java.util.HashMap;
import java.util.List;

public interface ApplyMapper {

    int deleteByPrimaryKey(String id);

    int insert(Apply record);

    int insertSelective(Apply record);

    Apply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Apply record);

    int updateByPrimaryKey(Apply record);

    int getCount();

    List<Apply> query(HashMap<String, Object> paramMap);
    int queryCount(HashMap<String, Object> paramMap);

}