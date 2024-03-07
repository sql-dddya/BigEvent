package org.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.ArticleMapper;
import org.example.pojo.Article;
import org.example.pojo.QueryResult;
import org.example.service.ArticleService;
import org.example.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

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

/*    @Override
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
    }*/

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
//        articleMapper.update(article);
        articleMapper.updateById(article);
    }

    @Override
    public QueryResult<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 分页条件
        Page<Article> p = new Page<>();
        p.setCurrent(pageNum);
        p.setSize(pageSize);

        // 排序条件
        p.addOrder(new OrderItem("category_id", false));

        // 查询条件 并将分页条件应用
        Page<Article> page = lambdaQuery()
                .eq(!ObjectUtils.isEmpty(categoryId), Article::getCategoryId, categoryId)
                .eq(state != null, Article::getState, state)
                .page(p);

        // 封装结果
        QueryResult<Article> qr = new QueryResult<>();
        qr.setTotal(page.getTotal());
        qr.setPage(page.getPages());
        qr.setItems(page.getRecords());
        return qr;
    }

    @Override
    public List<Article> listDetail(List<Integer> ids) {
        List<Article> list = articleMapper.listDetail(ids);
        return list;
    }

    @Override
    public List<Article> titleSelect(String name) {
        List<Article> list = articleMapper.titleSelect(name);
        return list;
    }

    @Override
    public List<Article> cadata() {
        List<Article> list = articleMapper.cadata();
        return list;
    }

    @Override
    public List<Article> articleList(String key, String state) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper();
        wrapper.like(Article::getContent, key)
                .eq(Article::getState, state);
        List<Article> list = articleMapper.articleList(wrapper);
        return list;
    }

//    @Override
//    public Article get(Integer id) {
//        return articleMapper.get(id);
//    }
//
//    @Override
//    public void delete(Integer id) {
//        articleMapper.delete(id);
//    }
}
