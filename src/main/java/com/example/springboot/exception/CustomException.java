package com.example.springboot.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends  RuntimeException {
    private String code;
    private String msg;

    public  CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
