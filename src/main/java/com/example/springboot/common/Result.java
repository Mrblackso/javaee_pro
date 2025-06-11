package com.example.springboot.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private String code;
    private String msg;
    private Object data;

    public Result() {
    }

    public static Result success() {
        Result result = new Result();
        result.setCode("200");
        result.setMsg("请求成功！");
        return result;
    }

    public static Result success(Object data) {
        Result result = success();
        result.setData(data);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode("500");
        result.setMsg("系统错误！");
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
