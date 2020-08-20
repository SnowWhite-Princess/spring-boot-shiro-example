package com.smart.example.common;

public enum ErrorStatus {
    SUCCESS(20000, "success"),
    EROR(40000, "eroor"),
    UN_AUTH_STATUS(40003, "no auth"),
    CREDENTIALS_ERROR_CODE(40010,"账号密码错误!");

    private int status;
    private String msg;

    ErrorStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
