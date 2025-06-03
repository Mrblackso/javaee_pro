package com.example.springboot.mapper;

import com.example.springboot.entity.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmployeeMapper {

    List<Employee> selectById(Integer id);

    void add(Employee employee);

    void updataById(Employee employee);

    void deleteById(Integer id);

    List<Employee> selectAll(@Param("employee") Employee employee);

    @Select( "select * from employee where username = #{username}")
    Employee selectByUsername(String username);
}
