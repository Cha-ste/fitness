package com.ocean.mapper;

import com.ocean.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface MessageMapper {

    int deleteByPrimaryKey(Integer nid);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    int getCount();

    List<Message> query(@Param("keyword") String keyword);

    int queryCount(HashMap<String, Object> paramMap);

}