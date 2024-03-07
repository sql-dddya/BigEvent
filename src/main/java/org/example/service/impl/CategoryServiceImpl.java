package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.CategoryMapper;
import org.example.pojo.Category;
import org.example.service.CategoryService;
import org.example.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(Category category) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        category.setCreateUser(id);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
//        categoryMapper.addCategory(category);
        categoryMapper.insert(category);
    }

//    @Override
//    public List<Category> get() {
//        List<Category> list = categoryMapper.get();
//        return list;
//    }

//    @Override
//    public Category getDetail(Integer id) {
//        return categoryMapper.getDetail(id);
//    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
//        categoryMapper.update(category);
        categoryMapper.updateById(category);
    }

//    @Override
//    public void delete(Integer id) {
//        categoryMapper.delete(id);
//    }

    @Override
    public Category getByName(String categoryName) {
        return categoryMapper.getByName(categoryName);
    }

}
