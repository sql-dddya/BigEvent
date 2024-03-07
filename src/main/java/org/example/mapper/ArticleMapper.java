package org.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    void add(Article article);

    List<Article> list(Integer userId, Integer categoryId, String state);

    List<Article> listDetail(List<Integer> ids);

    List<Article> titleSelect(String name);

    List<Article> cadata();

    List<Article> articleList(@Param("ew") LambdaQueryWrapper<Article> wrapper);

//    void update(Article article);

//    Article get(Integer id);
//
//    void delete(Integer id);
}
