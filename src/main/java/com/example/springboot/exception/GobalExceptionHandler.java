package com.example.springboot.exception;

//统一的异常处理

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//表示这是全局异常处理
//处理 com.example.springboot.controller 包下的所有异常
@ControllerAdvice("com.example.springboot.controller")
public class GobalExceptionHandler {
    @ExceptionHandler (Exception.class)// 拦截所有异常 Exception是所用 的异常类的· 父类
    @ResponseBody //返回 json数据
    public Result error(Exception e){
        e.printStackTrace();// 打印异常信息
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(CustomException e){
       
        return Result.error(e.getCode(),e.getMsg());
    }
}
