package com.example.springboot.service;



//service 用于处理 数据

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Article;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.ArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

//@Service 功能: 将当前类标记为 service 类,  交给 spring 管理

@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    public List<Article> selectAll(@Param("article") Article article) {
        return articleMapper.selectAll(article);
    }


    public Article selectById(Integer id) {
        return articleMapper.selectById(id);
    }

    public PageInfo<Article> selectPages(Article article, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> list = articleMapper.selectAll(article);
        return PageInfo.of(list);
    }

    public void add(Article article) {
        article.setTime(DateUtil.now());//添加时间

        articleMapper.add(article);
    }

    public void updata(Article article) {
        articleMapper.updataById(article);
    }

    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id:ids){
            this.deleteById(id);
        }
    }


}
