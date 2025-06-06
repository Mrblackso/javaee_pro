package com.example.springboot.service;



//service 用于处理 数据

import cn.hutool.core.util.StrUtil;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Employee;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//@Service 功能: 将当前类标记为 service 类,  交给 spring 管理

@Service
public class EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;
    public List<Employee> selectAll(@Param("employee") Employee employee) {
        return employeeMapper.selectAll(employee);
    }


    public Employee selectById(Integer id) {
        return employeeMapper.selectById(id);
    }

    public PageInfo<Employee> selectPages(Employee employee, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> list = employeeMapper.selectAll(employee);
        return PageInfo.of(list);
    }

    public void add(Employee employee) {
        String  username = employee.getUsername();
        Employee DbEmployee = employeeMapper.selectByUsername(username);
        if(DbEmployee != null){
//        数据库中有 该名字
            throw new CustomException("500", "用户已存在");
        }
        Employee DbEmployee1 = employeeMapper.selectByNO(employee.getNo());
        if(DbEmployee1 != null){
            throw new CustomException("500", "工号已存在");

        }

        if(StrUtil.isBlank(employee.getPassword())){
            employee.setPassword("emp");//默认密码
        }
        if(StrUtil.isBlank(employee.getName())){
            employee.setName(employee.getUsername());//默认名称
        }
        employee.setRole("EMP");
        employeeMapper.add(employee);

    }

    public void updata(Employee employee) {
        employeeMapper.updataById(employee);
    }

    public void deleteById(Integer id) {
        employeeMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id:ids){
            this.deleteById(id);
        }
    }

    public Employee login(Account account) {
        String  username = account.getUsername();
        Employee DbEmployee = employeeMapper.selectByUsername(username);
        if(DbEmployee == null){
//        数据库中没有 该名字
            throw new CustomException("500", "用户不存在");

        }

        String  password = account.getPassword();
        if(!DbEmployee.getPassword().equals(password)){
//            密码不匹配
            DbEmployee.setPassword(null);
            throw new CustomException("500", "密码错误");

        }


        return DbEmployee;
    }

    public void register(Employee employee) {
//        employeeMapper.add(employee);
        this.add(employee);
    }

    public void updatePassword(Account account) {
        Integer id=account.getId();
        Employee DbEmployee = this.selectById(id);
        if(!DbEmployee.getPassword().equals(account.getPassword())){
            //密码 不匹配
            throw new CustomException("500", "对不起，原密码错误");
        }
        DbEmployee.setPassword(account.getNewPassword());
        this.updata(DbEmployee);
    }
}
