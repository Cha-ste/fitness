package com.ocean.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ocean.entity.User;
import com.ocean.mapper.UserMapper;
import com.ocean.service.UserService;
import com.ocean.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper mapper;

    @Override
    public User getUser(Integer id) {

        User model = mapper.getUser(id);
        if (model == null) {
            logger.error("[getUser]delete User id={} fail", id);
            throw new RuntimeException("GET data fail");
        }
        return model;

    }

    @Override
    public void save(User model) {
        model.setPassword(MD5Util.inputPass2FormPass(model.getPassword()));
        int success = mapper.insert(model);
        if (success <= 0) {
            logger.error("[addUser]add User={} fail",  model.toString());
            throw new RuntimeException("Add data fail");
        }
        return;

    }

    @Override
    public void update(User model) {
        int success = mapper.updateByPrimaryKeySelective(model);
        if (success <= 0) {
            logger.error("[updateUser]update User={} fail",  model.toString());
            throw new RuntimeException("Modify data fail");
        }
        return;

    }

    @Override
    public void del(Integer sid) {

        int success = mapper.deleteByPrimaryKey(sid);
        if (success <= 0) {
            logger.error("[deleteUser]delete User id={} fail", sid);
            throw new RuntimeException("Del data fail");
        }
        return;

    }

    @Override
    public PageInfo<User> query(int pageNum, int pageSize, String username) {
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo<>(mapper.query(username));
    }

    @Override
    public int getCount() {
        return mapper.getCount();
    }

    @Override
    public int queryCount(HashMap<String, Object> paramMap) {
        return mapper.queryCount(paramMap);
    }

    @Override
    public boolean usernameExist(String username) {
        User member = mapper.getByUsername(username);
        return member != null;
    }

    @Override
    public User getUserForLogin(User user) {
        return mapper.getUserForLogin(user.getUsername(),
                MD5Util.inputPass2FormPass(user.getPassword()));
    }

    @Override
    public void changePassword(Integer sid, String newPassword) {
        mapper.changePassword(sid, MD5Util.inputPass2FormPass(newPassword));
    }
}