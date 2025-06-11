package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Article;
import com.example.springboot.service.ArticleService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import javax.annotation.Resource;
import java.util.List;

@Tag(name = "文章接口")
@RestController
@RequestMapping("/article")
public class ArticleController {

//    @Resource
    @Autowired
    private ArticleService articleService;


//    查询所有数据
    @GetMapping("/selectAll")
    public Result selectAll(Article article){
        if (article == null) {
            article = new Article();
        }
        List<Article> list = articleService.selectAll(article);
        return Result.success(list);
    }


    @Operation(summary = "查询指定的文章", description = "根据 id 查询")
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Article list = articleService.selectById(id);
        return Result.success(list);
    }

    @Operation(summary = "分页查询")
    @GetMapping("/selectPages")
    public Result selectPages(
            Article  article,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Article> list = articleService.selectPages(article,pageNum, pageSize);
        return Result.success(list);
    }

//    @RequestBody 将前端传来的json 文件 转换成 Article（java的） 对象
    @Operation(summary = "添加文章")
    @PostMapping("/add")
    public Result add(@RequestBody Article article) {
        if (article.getTitle() == null || article.getTitle().isEmpty()) {
            return Result.error("400", "标题不能为空");
        }
        if (article.getContent() == null || article.getContent().isEmpty()) {
            return Result.error("400", "内容不能为空");
        }
        System.out.println("接收到的文章数据：" + article); // 调试日志
        articleService.add(article);
        return Result.success();
    }

    @Operation(summary = "更新文章")
    @PostMapping("/updata")
    public Result updata(@RequestBody Article article){
        articleService.updata(article);
        return Result.success();
    }
    @Operation(summary = "删除指定文章", description = "根据 id 删除指定文章")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        articleService.deleteById(id);
        return Result.success();
    }


//    批量删除
    @Operation(summary = "批量删除")
    @DeleteMapping("deleteBatch")
//    一个List 集合 @RequestBody 接收
    public Result deleteBatch(@RequestBody List<Integer> ids){
        articleService.deleteBatch(ids);
        return Result.success();
    }
}
