package com.example.springboot.mapper;

import com.example.springboot.entity.Employee;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface EmployeeMapper {
    List<Employee> selectAll();

    List<Employee> selectById(Integer id);

    void add(Employee employee);

    void updataById(Employee employee);

    void deleteById(Integer id);
}
