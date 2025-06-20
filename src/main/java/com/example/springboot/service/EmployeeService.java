package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Employee;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

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
            throw new CustomException("500", "用户不存在");

        }

        String  password = account.getPassword();
        if(!DbEmployee.getPassword().equals(password)){
            DbEmployee.setPassword(null);
            throw new CustomException("500", "密码错误");

        }


        return DbEmployee;
    }

    public void register(Employee employee) {
        this.add(employee);
    }

    public void updatePassword(Account account) {
        Integer id=account.getId();
        Employee DbEmployee = this.selectById(id);
        if(!DbEmployee.getPassword().equals(account.getPassword())){
            throw new CustomException("500", "对不起，原密码错误");
        }
        DbEmployee.setPassword(account.getNewPassword());
        this.updata(DbEmployee);
    }
}
