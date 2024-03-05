package org.example.service.impl;


import com.github.pagehelper.PageHelper;
import org.example.mapper.ArticleMapper;
import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.example.service.ArticleService;
import org.example.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        article.setCreateUser(id);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 创建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        // 开启分页查询
        PageHelper.startPage(pageNum,pageSize);

        // 查询
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId,categoryId, state);

        // 返回结果
        pb.setTotal(Long.valueOf(as.size()));
        pb.setItems(as);
        return pb;
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public Article get(Integer id) {
        return articleMapper.get(id);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
