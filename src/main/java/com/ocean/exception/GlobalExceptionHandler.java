package com.ocean.exception;

import com.ocean.vo.CodeMsg;
import com.ocean.vo.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.zip.DataFormatException;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * http请求的方法不正确
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)

    public ResultBean<String> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        logger.error("http请求的方法不正确:【" + e.getMessage() + "】");
        return ResultBean.error(CodeMsg.NOT_ALLOWED);
    }

    /**
     * 请求参数不全
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultBean<String> misParamOrMatchExceptionHandler(MissingServletRequestParameterException e) {
        logger.error("请求参数不全:【" + e.getMessage() + "】");
        return ResultBean.error(CodeMsg.BAD_REQUEST.fillArgs(e.getMessage()));
    }

    /**
     * 数据格式不正确
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResultBean<String> dataFormatExceptionHandler(HttpMessageNotReadableException e) {
        logger.error("数据格式不正确:【" + e.getMessage() + "】");
        return ResultBean.error(CodeMsg.ILLEGAL_ARGUMENT.fillArgs(e.getMessage()));
    }

    /**
     * 非法输入
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultBean<String> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logger.error("非法输入:【" + e.getMessage() + "】");
        return ResultBean.error(CodeMsg.ILLEGAL_ARGUMENT);
    }

    /**
     * 其他异常
     */
    @ExceptionHandler  //处理其他异常
    public ResultBean<String> allExceptionHandler(Exception e) {

        if(e instanceof BindException) {
            BindException exception = (BindException) e;
            List<ObjectError> allErrors = exception.getAllErrors();
            ObjectError objectError = allErrors.get(0);
            String msg = objectError.getDefaultMessage();
            logger.error("具体错误信息:【" + msg + "】");
            return ResultBean.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        } else {
            int count = 0; //只打印15行的错误堆栈
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                logger.error(stackTraceElement.toString());
                if (count++ > 13) break;
            }
            return ResultBean.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultBean<String> nullPointerExceptionHandler(NullPointerException e) {
        logger.error("空指针异常:【" + e.getMessage() + "】");
        return ResultBean.error(CodeMsg.SERVER_ERROR);
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public ResultBean<String> classCastExceptionHandler(NullPointerException e) {
        logger.error("类型转换异常:【" + e.getMessage() + "】");
        return ResultBean.error(CodeMsg.SERVER_ERROR);
    }

    /**
     * 参数校验错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBean<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = getInvalidateMessage(e);
        logger.error("参数错误:【" + message + "】");
        return ResultBean.error(CodeMsg.ILLEGAL_ARGUMENT.fillArgs(message));
    }

    /**
     * 拼接参数验证错误信息字符串
     */
    private String getInvalidateMessage(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder sb = new StringBuilder();
        for(ObjectError error : allErrors) {
            sb.append("；").append(error.getDefaultMessage());
        }
        String result = sb.substring(1, sb.length()).toString();
        return result;
    }


}
