package com.example.springboot.service;

import cn.hutool.core.date.DateUtil;
import com.example.springboot.entity.Article;
import com.example.springboot.mapper.ArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleMapper articleMapper;

    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public List<Article> selectAll(Article article) {
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


    public Integer selectCountByDate(String date) {
        return articleMapper.selectCountByDate(date);
    }
}
