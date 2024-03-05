package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    void add(Article article);

    List<Article> list(Integer userId, Integer categoryId, String state);

    void update(Article article);

    Article get(Integer id);

    void delete(Integer id);
}
