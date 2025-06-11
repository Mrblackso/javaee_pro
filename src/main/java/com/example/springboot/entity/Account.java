package com.example.springboot.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private Integer id;
    // 密码
    private String password;
    // 用户名
    private String username;
    // 角色
    private String role;
    // 名称
    private String name;

    private String newPassword;

    private String avatar;

}
