package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Insert("insert into employee (username,name,phone,sex,id_number) values (#{username},#{name},#{phone},#{sex},#{idNumber})")
    void save(EmployeeDTO employeeDTO);

    //根据姓名查询员工信息
    Page selectEmployeeByName(EmployeePageQueryDTO pageinfo);

    /**
     * 修改员工信息
     * @param employee
     */
    void update(Employee employee);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee selectById(Long id);
}
