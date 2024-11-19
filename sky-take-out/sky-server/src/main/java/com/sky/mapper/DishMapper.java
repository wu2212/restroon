package com.sky.mapper;

import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select * from dish where category_id = #{id}")
    List<Dish> selectByCategoryId(Long id);
}
