package com.ocean.service;

import com.github.pagehelper.PageInfo;
import com.ocean.entity.Message;

import java.util.HashMap;


public interface MessageService {

    Message getMessage(Integer id);

    void save(Message model);

    void update(Message model);

    void del(Integer id);

    int getCount();

    PageInfo<Message> query(int currentPage, int itemsPerPage, String keyword);

    int queryCount(HashMap<String, Object> paramMap);

}