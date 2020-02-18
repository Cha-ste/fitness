package com.ocean.vo;

/**
 * 请求结果返回对象
 *
 * @param <T>
 */
public class ResultBean<T> {
    private int code;
    private String msg;
    private T data;

    public static ResultBean ERROR = new ResultBean(CodeMsg.SERVER_ERROR);

    private ResultBean(T data) {
        code = 1;
        msg = "success";
        this.data = data;
    }

    private ResultBean(CodeMsg codeMsg) {
        if(codeMsg == null) {
            return;
        }
        code = codeMsg.getCode();
        msg = codeMsg.getMsg();
    }

    /**
     * 成功时调用
     *
     * @param data 返回数据
     * @param <T> 类型
     * @return 统一响应结果
     */
    public static <T>ResultBean<T> success(T data) {
        return new ResultBean<>(data);
    }

    /**
     * 异常时调用
     *
     * @param codeMsg 状态码&信息对象
     * @param <T> 类型
     * @return 统一响应结果
     */
    public static <T>ResultBean<T> error(CodeMsg codeMsg) {
        return new ResultBean<>(codeMsg);
    }

    /**
     * 服务器异常，自定义错误信息
     * @param msg 错误信息
     * @param <T> 类型
     * @return 统一响应结果
     */
    public static <T>ResultBean<T> errorMsg(String msg) {
        CodeMsg codeMsg = new CodeMsg(500100, msg);
        return error(codeMsg);
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
