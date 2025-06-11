package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Employee;
import com.example.springboot.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
//import javax.annotation.Resource;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/employee")
@Tag(name = "员工接口")
public class EmployeeController {

//    @Resource
    @Autowired
    private EmployeeService employeeService;


//    查询所有数据
    @Operation(summary = "查询所有用户")
    @GetMapping("/selectAll")
    public Result selectAll(Employee  employee){
        List<Employee> list = employeeService.selectAll(employee);
        return Result.success(list);
    }

    @Operation(summary = "查询指定用户", description = "根据 id 查询用户")
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Employee list = employeeService.selectById(id);
        return Result.success(list);
    }

    @Operation(summary = "分页查询", description = "设置分页查询，每 10 页进行查询")
    @GetMapping("/selectPages")
    public Result selectPages(
            Employee  employee,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Employee> list = employeeService.selectPages(employee,pageNum, pageSize);
        return Result.success(list);
    }

//    @RequestBody 将前端传来的json 文件 转换成 Employee（java的） 对象
    @Operation(summary = "添加员工")
    @PostMapping("/add")
    public Result add(@RequestBody Employee employee){
        employeeService.add(employee);
        return Result.success();
    }

    @Operation(summary = "更新员工数据")
    @PostMapping("/updata")
    public Result updata(@RequestBody Employee employee){
        employeeService.updata(employee);
        return Result.success();
    }

    @Operation(summary = "删除指定员工", description = "根据 id 删除指定员工")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        employeeService.deleteById(id);
        return Result.success();
    }


//    批量删除
    @Operation(summary = "批量删除", description = "根据员工集合 id 删除员工")
    @DeleteMapping("deleteBatch")
//    一个List 集合 @RequestBody 接收
    public Result deleteBatch(@RequestBody List<Integer> ids){
        employeeService.deleteBatch(ids);
        return Result.success();
    }
}
