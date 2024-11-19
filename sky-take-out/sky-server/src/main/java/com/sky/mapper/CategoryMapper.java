package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CategoryMapper {

    /**
     * 获取分类
     * @param categoryPageQueryDTO
     * @return
     */
    Page getCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 删除分类
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void delete(Long id);

    /**
     * 新增分类
     * @param categoryDTO
     */
    @Insert("insert into category (type,name,sort) values " +
            "(#{type},#{name},#{sort})")
    void insert(CategoryDTO categoryDTO);

    /**
     * 修改分类
     * @param categoryDTO
     */
    @Update("update category set name = #{name},sort = #{sort} where id = #{id}")
    void update(CategoryDTO categoryDTO);
}
