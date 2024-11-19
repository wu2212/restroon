package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页获取分类
     */
    @GetMapping("/page")
    Result<PageResult> getPage(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult pageResult = categoryService.getPage(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    Result deleteCategory(Long id){
        categoryService.delete(id);
        return Result.success();
    }

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    Result insertCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.insertCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 修改分类
     */
    @PutMapping
    Result updateCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

}
