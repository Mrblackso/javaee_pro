package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Employee;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.AdminService;
import com.example.springboot.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


//标注这个是接口 返回 json
@RestController()
public class WebController {

    @Resource
    private EmployeeService employeeService;

    @Resource
    private AdminService adminService;
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
    public Result login(@RequestBody Account account){
        Account result=null;
        if(("ADMIN").equals(account.getRole())){
           result = adminService.login(account);
        }else if(("EMP").equals(account.getRole())){
            result= employeeService.login(account);
        }else {
            return Result.error("400","角色错误");
        }

        return Result.success(result);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Employee employee) {
        employeeService.register(employee);
        return Result.success();
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {


       Account result = null;

//        信息已经传入
        System.out.println(account.getRole());
        System.out.println("ADMIN");
//        System.out.println("EMP");

        if("ADMIN".equals(account.getRole())){
            //System.out.println("now step in");
            adminService.updatePassword(account);
        }else if("EMP".equals(account.getRole())){
            employeeService.updatePassword(account);
        }
        else{
            return Result.error("400","非法角色");
        }
        return Result.success();
    }

}
