package com.example.springboot.mapper;

import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {

    List<Admin> selectById(Integer id);

    void add(Admin admin);

    void updataById(Admin admin);

    void deleteById(Integer id);

    List<Admin> selectAll(@Param("admin") Admin admin);

    @Select( "select * from admin where username = #{username}")
    Admin selectByUsername(String username);
}
