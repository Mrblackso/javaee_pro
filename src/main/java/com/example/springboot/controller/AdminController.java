package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.AdminService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "管理员接口")
@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;


//    查询所有数据
    @Operation(summary = "查询所有管理员")
    @GetMapping("/selectAll")
    public Result selectAll(Admin  admin){
        List<Admin> list = adminService.selectAll(admin);
        return Result.success(list);
    }

    @Operation(summary = "查询指定的管理员", description = "根据 id 查询")
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Admin list = adminService.selectById(id);
        return Result.success(list);
    }

    @Operation(summary = "分页查询")
    @GetMapping("/selectPages")
    public Result selectPages(
            Admin  admin,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Admin> list = adminService.selectPages(admin,pageNum, pageSize);
        return Result.success(list);
    }

//    @RequestBody 将前端传来的json 文件 转换成 Admin（java的） 对象
    @Operation(summary = "添加管理员用户")
    @PostMapping("/add")
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return Result.success();
    }

    @Operation(summary = "更新管理员用户信息")
    @PostMapping("/updata")
    public Result updata(@RequestBody Admin admin){
        adminService.updata(admin);
        return Result.success();
    }

    @Operation(summary = "删除指定用户", description = "根据 id 删除指定用户")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }


//    批量删除
    @Operation(summary = "批量删除")
    @DeleteMapping("deleteBatch")
//    一个List 集合 @RequestBody 接收
    public Result deleteBatch(@RequestBody List<Integer> ids){
        adminService.deleteBatch(ids);
        return Result.success();
    }
}
