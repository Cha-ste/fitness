package com.ocean.service.serviceImpl;

import com.ocean.entity.User;
import com.ocean.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import com.ocean.entity.Coach;
import com.ocean.service.CoachService;
import com.ocean.mapper.CoachMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CoachServiceImpl implements CoachService{

    private static final Logger logger = LoggerFactory.getLogger(CoachServiceImpl.class);

    @Autowired
    private CoachMapper mapper;

    @Override
    public Coach getCoach(Integer id) {

        Coach model = mapper.getCoach(id);
        if (model == null) {
            logger.error("[getCoach]delete Coach id={} fail", id);
            throw new RuntimeException("GET data fail");
        }
        return model;

    }

    @Override
    public void save(Coach model) {
        // 密码加密存储
        model.setPassword(MD5Util.inputPass2FormPass(model.getPassword()));
        int success = mapper.insert(model);
        if (success <= 0) {
            logger.error("[addCoach]add Coach={} fail",  model.toString());
            throw new RuntimeException("Add data fail");
        }
        return;

    }

    @Override
    public void update(Coach model) {
        // 密码加密存储
        model.setPassword(MD5Util.inputPass2FormPass(model.getPassword()));
        int success = mapper.updateByPrimaryKeySelective(model);
        if (success <= 0) {
            logger.error("[updateCoach]update Coach={} fail",  model.toString());
            throw new RuntimeException("Modify data fail");
        }
        return;

    }

    @Override
    public void del(Integer tid) {

        int success = mapper.deleteByPrimaryKey(tid);
        if (success <= 0) {
            logger.error("[deleteCoach]delete Coach id={} fail", tid);
            throw new RuntimeException("Del data fail");
        }
        return;

    }

    @Override
    public PageInfo<Coach> query(int pageNum, int pageSize, String coachName) {
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo<>(mapper.query(coachName));
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
    public List<User> getTraineeList(Integer tid, Integer cid) {
        return mapper.getTraineeList(tid, cid);
    }

    @Override
    public void changePassword(Integer tid, String newPassword) {
        mapper.changePassword(tid, MD5Util.inputPass2FormPass(newPassword));
    }

    @Override
    public Coach getCoachForLogin(String coachName, String password) {
        Coach model = mapper.getCoachForLogin(coachName, MD5Util.inputPass2FormPass(password));
        return model;
    }
}