package com.ocean.mapper;

import com.ocean.entity.Apply;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ApplyMapper {

    int deleteByPrimaryKey(String id);

    int insert(Apply record);

    int updateByPrimaryKey(Apply record);

    List<Apply> query(HashMap<String, Object> paramMap);

    int delete(@Param("cid") Integer cid, @Param("sid") Integer sid);

    int changeTable(Apply apply);

    Apply getApply(@Param("cid") Integer cid, @Param("sid") Integer sid);
}
