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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

//@Service 功能: 将当前类标记为 service 类,  交给 spring 管理

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;
    public List<Admin> selectAll(@Param("admin") Admin admin) {
        return adminMapper.selectAll(admin);
    }


    public Admin selectById(Integer id) {
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


    public void updatePassword(Account account) {

        Integer id=account.getId();

        if(id==null){
            //System.out.println("500 不存在");
            throw new CustomException("500", "用户不存在");
        }
        Admin DbAdmin = this.selectById(id);

//        System.out.println(DbAdmin.getId());
//        System.out.println(DbAdmin.getPassword());
//        System.out.println("<-------------------->");
//        System.out.println(account.getPassword());
//        System.out.println(account.getNewPassword());
//        System.out.println(DbAdmin.getPassword());
//        System.out.println("<-------------------->");

        System.out.println(DbAdmin.getPassword());
        System.out.println(account.getPassword());
        System.out.println(account.getNewPassword());
        if(!DbAdmin.getPassword().equals(account.getPassword())){
            //密码 不匹配
            throw new CustomException("500", "对不起，原密码错误");
        }
        DbAdmin.setPassword(account.getNewPassword());

        this.updata(DbAdmin);
    }

//    public void register(Admin admin) {
//        this.add(admin);
//    }
}
