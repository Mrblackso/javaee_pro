package com.example.springboot.mapper;

import com.example.springboot.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper {

    Article selectById(Integer id);

    void add(Article article);

    void updataById(Article article);

    void deleteById(Integer id);

    List<Article> selectAll(@Param("article") Article article);

//    @Select( "select * from article where username = #{username}")
//    Article selectByUsername(String username);
}
