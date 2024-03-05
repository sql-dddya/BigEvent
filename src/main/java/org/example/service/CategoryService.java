package org.example.service;

import org.example.pojo.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> get();

    Category getDetail(Integer id);

    void update(Category category);

    void delete(Integer id);

    Category getByName(String categoryName);
}
