package com.wang.database.config;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2022/10/19
 * @change 2022/10/19 by wangxiaofei for init
 **/
public class McpException extends RuntimeException{
    private String msgCode;

    public McpException() {
    }

    public McpException(String message) {
        super(message);
    }

    public McpException(String message, String msgCode) {
        super(message);
        this.msgCode = msgCode;
    }

    public String getMsgCode() {
        return this.msgCode;
    }
}
