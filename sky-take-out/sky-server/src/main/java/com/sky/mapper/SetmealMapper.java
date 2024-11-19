package com.sky.mapper;

import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @Select("select * from setmeal where category_id = #{id}")
    List<Setmeal> selectByCategoryId(Long id);
}
