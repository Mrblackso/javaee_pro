package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Employee;
import com.example.springboot.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;


//    查询所有数据
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Employee> list = employeeService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        List<Employee> list = employeeService.selectById(id);
        return Result.success(list);
    }


    @GetMapping("/selectPages")
    public Result selectPages(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Employee> list = employeeService.selectPages(pageNum, pageSize);
        return Result.success(list);
    }

//    @RequestBody 将前端传来的json 文件 转换成 Employee（java的） 对象
    @PostMapping("/add")
    public Result add(@RequestBody Employee employee){
        employeeService.add(employee);
        return Result.success();
    }

    @PostMapping("/updata")
    public Result updata(@RequestBody Employee employee){
        employeeService.updata(employee);
        return Result.success();
    }

    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        employeeService.deleteById(id);
        return Result.success();
    }
}
