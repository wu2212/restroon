package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);


    /**
     * 添加员工
     * @param
     * @return
     */
    void save(EmployeeDTO employeeDTO);





    /**
     * 分页查询员工
     */
    PageResult selectEmployeePage(EmployeePageQueryDTO pageinfo);

    /**
     * 修改员工状态
     * @param status
     * @param id
     */
    void StartorStop(Integer status, Long id);


    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee selectById(Long id);

    /**
     * 修改员工信息
     * @param employee
     */
    void updateEmployee(Employee employee);
}
