package com.ocean.mapper;

import com.ocean.entity.Apply;
import org.apache.ibatis.annotations.Param;

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

    int delete(@Param("cid") Integer cid, @Param("sid") Integer sid);

    int changeTable(Apply apply);
}
