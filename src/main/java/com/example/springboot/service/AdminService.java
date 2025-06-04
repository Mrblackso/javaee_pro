package com.example.springboot.service;



//service 用于处理 数据

import cn.hutool.core.util.StrUtil;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//@Service 功能: 将当前类标记为 service 类,  交给 spring 管理

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;
    public List<Admin> selectAll(@Param("admin") Admin admin) {
        return adminMapper.selectAll(admin);
    }


    public List<Admin> selectById(Integer id) {
        return adminMapper.selectById(id);
    }

    public PageInfo<Admin> selectPages(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        String  username = admin.getUsername();
        Admin DbAdmin = adminMapper.selectByUsername(username);
        if(DbAdmin != null){
//        数据库中有 该名字
            throw new CustomException("500", "用户已存在");
        }
        if(StrUtil.isBlank(admin.getPassword())){
            admin.setPassword("admin");//默认密码
        }
        if(StrUtil.isBlank(admin.getName())){
            admin.setName(admin.getUsername());//默认名称
        }
        admin.setRole("ADMIN");
        adminMapper.add(admin);

    }

    public void updata(Admin admin) {
        adminMapper.updataById(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id:ids){
            this.deleteById(id);
        }
    }

    public Admin login(Account account) {
        String  username = account.getUsername();
        Admin DbAdmin = adminMapper.selectByUsername(username);
        if(DbAdmin == null){
//        数据库中没有 该名字
            throw new CustomException("500", "用户不存在");

        }
        String  password = account.getPassword();
        if(!DbAdmin.getPassword().equals(password)){
//            密码不匹配
            DbAdmin.setPassword(null);
            throw new CustomException("500", "密码错误");

        }
        return DbAdmin;
    }

//    public void register(Admin admin) {
//        this.add(admin);
//    }
}
