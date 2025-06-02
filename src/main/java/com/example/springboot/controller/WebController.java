package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Employee;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


//标注这个是接口 返回 json
@RestController()
public class WebController {

    @Resource
    private EmployeeService employeeService;
//    可以通过get 请求
    @GetMapping("/hello")
    public  Result hello() {
        return Result.success("hello");
    }

    @GetMapping("/weather")
    public Result weather() {
        return Result.success("today's weather is sunny! qwq");
    }

    @GetMapping("/count")
    public Result count() {
//        int res=1/0;
        throw new CustomException("400", "error" );
//        throw new  RuntimeException("error");// 程序自己抛出一个异常
//        return Result.success(114514);
    }


    //返回一个 json 数据
    @GetMapping("/map")
    public Result map() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","zcj");
        map.put("age",18);
        return Result.success(map);
    }

    @PostMapping("/login")
    public Result login(@RequestBody Employee employee){
        Employee DbEmployee = employeeService.login(employee);
        return  Result.success(DbEmployee);
    }

}
