package com.smart.example.common;

import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseResult<Object> handler(Exception e){
        if (e instanceof CredentialsException){
            return ResponseResult.error(ErrorStatus.CREDENTIALS_ERROR_CODE);
        }else if (e instanceof UnknownAccountException){
            return ResponseResult.error(ErrorStatus.CREDENTIALS_ERROR_CODE);
        }
        return ResponseResult.error();
    }

}
