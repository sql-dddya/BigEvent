package org.example.controller;

import org.example.pojo.Category;
import org.example.pojo.Result;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增文章分类
     * @param category 分类名、分类别名
     * @return
     */
    @PostMapping
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category){
        if(categoryService.getByName(category.getCategoryName()) != null){
            return Result.error("该分类已经存在");
        }
        categoryService.addCategory(category);
        return Result.success();
    }

    /**
     * 获取文章分类列表
     * @return
     */
    @GetMapping
    public Result get(){
//        List<Category> list = categoryService.get();
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    /**
     * 获取文章分类详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result getDetail(@RequestParam Integer id){
//        Category category = categoryService.getDetail(id);
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    /**
     * 更新文章分类
     * @param category ID、分类名、分类别名
     * @return
     */
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }

    /**
     * 删除某个文章分类
     * @param id
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
//        categoryService.delete(id);
        categoryService.removeById(id);
        return Result.success();
    }

}
