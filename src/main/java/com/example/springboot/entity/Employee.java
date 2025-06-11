package com.example.springboot.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

//实体类
@Getter
@Setter
public class Employee extends Account{
    // ID
    private Integer id;
    // 密码
    private String password;
    // 用户名
    private String username;
    // 角色
    private String role;
    // 名称
    private String name;
    // 性别
    private String sex;
    // 工号
    private String no;
    // 年龄
    private Integer age;
    // 个人介绍
    private String description;
    // 部门
    private Integer departmentId;
}
