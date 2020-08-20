package com.smart.example.common;

import lombok.Data;

@Data
public  class ResponseResult<T> {
    private int status;
    private String  msg;
    private T data;

    public static  <T>  ResponseResult<T>  success(T data){
        ResponseResult<T> result = new ResponseResult<>();
        result.setStatus(ErrorStatus.SUCCESS.getStatus());
        result.setMsg(ErrorStatus.SUCCESS.getMsg());
        result.setData(data);
        return result;
    };

    public static  <T>  ResponseResult<T>  success(ErrorStatus status,T data){
        ResponseResult<T> result = new ResponseResult<>();
        result.setStatus(status.getStatus());
        result.setMsg(status.getMsg());
        result.setData(data);
        return result;
    };

    public static  <T>  ResponseResult<T>  error(){
        ResponseResult<T> result = new ResponseResult<>();
        result.setStatus(ErrorStatus.EROR.getStatus());
        result.setMsg(ErrorStatus.EROR.getMsg());
        return result;
    };



    public static  <T>  ResponseResult<T>  error(ErrorStatus status){
        ResponseResult<T> result = new ResponseResult<>();
        result.setStatus(status.getStatus());
        result.setMsg(status.getMsg());
        return result;
    };



}
