package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void addCategory(Category category);

    List<Category> get();

    Category getDetail(Integer id);

    void update(Category category);

    void delete(Integer id);

    Category getByName(String categoryName);
}
