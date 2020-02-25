package com.ocean.vo;

/**
 * 状态码 & 信息类
 */
public class CodeMsg {
    private int code;
    private String msg;


    //通用
    public static CodeMsg NOT_ALLOWED = new CodeMsg(400100, "http请求方法不正确");
    public static CodeMsg BAD_REQUEST = new CodeMsg(400101, "http请求参数不全:%s");
    public static CodeMsg ILLEGAL_ARGUMENT = new CodeMsg(400102, "参数错误:%s");
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务器异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");


    //登录模块 5002xx
    public static CodeMsg NO_LOGIN = new CodeMsg(5002001, "用户未登录");
    public static CodeMsg TOKEN_EXPIRED = new CodeMsg(5002002, "token非法或token已过期");
    public static CodeMsg NO_USER = new CodeMsg(5002003, "用户不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(5002004, "密码不正确");

    //其他模块 5003xx

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 手动填写错误信息
     *
     * @param args 异常参数列表
     * @return CodeMsg
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.getCode();
        String msg = String.format(this.msg, args);
        return new CodeMsg(code, msg);
    }
}
