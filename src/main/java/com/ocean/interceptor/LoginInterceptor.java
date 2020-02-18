package com.ocean.interceptor;


import com.alibaba.fastjson.JSON;
import com.ocean.redis.RedisService;
import com.ocean.redis.UserPrefix;
import com.ocean.utils.Constants;
import com.ocean.utils.TokenUtils;
import com.ocean.vo.CodeMsg;
import com.ocean.vo.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if(StringUtils.isEmpty(token)){
            logger.info("用户未登录");
            printJson(response, CodeMsg.NO_LOGIN);
            return false;
        }

        if (!validToken(token)) {
            printJson(response, CodeMsg.TOKEN_EXPIRED);
            return false;
        }

        //延迟token过期时间
        delayExpired(token);

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With, token");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
    }

    private static void printJson(HttpServletResponse response, CodeMsg codeMsg) {
        String content = JSON.toJSONString(ResultBean.error(codeMsg));
        printContent(response, content);
    }


    private static void printContent(HttpServletResponse response, String content) {
        try {
            response.reset();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(content);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean validToken(String token) {
        String userId = redisService.get(UserPrefix.getByToken, token, String.class);
        return !StringUtils.isEmpty(userId);
    }

    private void delayExpired(String token) {
        String userId = redisService.get(UserPrefix.getByToken, token, String.class);
        redisService.set(UserPrefix.getByToken, token, userId, Constants.TOKEN_EXPIRED_SECOND);
    }

}
