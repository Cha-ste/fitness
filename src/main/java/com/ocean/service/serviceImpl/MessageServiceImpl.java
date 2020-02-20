package com.ocean.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ocean.entity.Message;
import com.ocean.mapper.MessageMapper;
import com.ocean.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageMapper mapper;

    @Override
    public Message getMessage(Integer id) {

        Message model = mapper.selectByPrimaryKey(id);
        if (model == null) {
            logger.error("[getMessage]delete Message id={} fail", id);
            throw new RuntimeException("GET data fail");
        }
        return model;

    }

    @Override
    public void save(Message model) {
        int success = mapper.insert(model);
        if (success <= 0) {
            logger.error("[addMessage]add Message={} fail", model.toString());
            throw new RuntimeException("Add data fail");
        }
        return;

    }

    @Override
    public void update(Message model) {
        int success = mapper.updateByPrimaryKeySelective(model);
        if (success <= 0) {
            logger.error("[updateMessage]update Message={} fail", model.toString());
            throw new RuntimeException("Modify data fail");
        }
        return;

    }

    @Override
    public void del(Integer id) {

        int success = mapper.deleteByPrimaryKey(id);
        if (success <= 0) {
            logger.error("[deleteMessage]delete Message id={} fail", id);
            throw new RuntimeException("Del data fail");
        }
        return;

    }

    @Override
    public PageInfo<Message> query(int pageNum, int pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo<>(mapper.query(keyword));
    }

    @Override
    public int getCount() {
        return mapper.getCount();
    }

    @Override
    public int queryCount(HashMap<String, Object> paramMap) {
        return mapper.queryCount(paramMap);
    }

}