package com.ocean.controller;

import com.github.pagehelper.PageInfo;
import com.ocean.entity.Message;
import com.ocean.service.MessageService;
import com.ocean.vo.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@RestController("MessageController")
@RequestMapping("/message")
@Api(tags = "消息相关接口")
public class MessageController {

    public static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService service;

    @GetMapping(value = "/get")
    @ApiOperation("获取消息信息")
    public ResultBean<Message> get(@RequestParam Integer id) {
        try {
            Message entity = service.getMessage(id);
            return ResultBean.success(entity);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.errorMsg("数据不存在");
        }
    }

    @GetMapping(value = "/query")
    @ResponseBody
    @ApiOperation("消息列表（带分页，可根据标题，或者内容模糊搜索）")
    public ResultBean<PageInfo<Message>> query(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String keyword) {
        if (pageNum == null || pageNum.equals(0)) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize.equals(0)) {
            pageSize = 20;
        }
        if (StringUtils.isEmpty(keyword)) {
            keyword = null;
        }

        PageInfo<Message> pageInfo = service.query(pageNum, pageSize, keyword);
        return ResultBean.success(pageInfo);
    }

    @PostMapping(value = "/save")
    @ApiOperation("发布或者修改消息（当nid为空时->发布，否则->修改）")
    public ResultBean save(@Validated Message model) {
        try {
            Message record = new Message();
            BeanUtils.copyProperties(model, record);
            record.setPtime(new Date());

            if (com.ocean.utils.StringUtils.isNullOrZero(model.getNid())) {
                service.save(record);
                return ResultBean.success("发布成功");
            } else {
                service.update(record);
                return ResultBean.success("修改成功");
            }

        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.ERROR;
        }
    }

    @PostMapping(value = "/del")
    @ApiOperation("删除消息")
    public ResultBean del(Integer nid) {
        try {
            service.del(nid);
        } catch (Exception e) {
            logger.error("Fail:", e);
            return ResultBean.errorMsg("消息不存在，删除失败");
        }
        return ResultBean.success("删除成功");
    }

}
