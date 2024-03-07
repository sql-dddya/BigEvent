package org.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.logging.log4j.MarkerManager;
import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.example.pojo.QueryResult;
import org.example.pojo.Result;
import org.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    /**
     * 分页查询文章
     * @param pageNum
     * @param pageSize
     * @param categoryId  不是必须
     * @param state 不是必须
     * @return
     */
    @GetMapping
    public Result<QueryResult<Article>> list(Integer pageNum, Integer pageSize,
                                             @RequestParam(required = false) Integer categoryId,
                                             @RequestParam(required = false) String state){

//        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        QueryResult<Article> p = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(p);
    }

    /**
     * 更新文章
     * @param article
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Article article){
        articleService.update(article);
        return Result.success();
    }

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result get(@RequestParam Integer id){
//        Article article = articleService.get(id);
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
//        articleService.delete(id);
        articleService.removeById(id);
        return Result.success();
    }
}
