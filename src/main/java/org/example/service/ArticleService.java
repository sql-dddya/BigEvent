package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.example.pojo.QueryResult;

import java.util.List;

public interface ArticleService extends IService<Article> {
    void add(Article article);

//    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void update(Article article);

    QueryResult<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    List<Article> listDetail(List<Integer> ids);

    List<Article> titleSelect(String name);

    List<Article> cadata();

    List<Article> articleList(String key, String state);

//    Article get(Integer id);
//
//    void delete(Integer id);
}
