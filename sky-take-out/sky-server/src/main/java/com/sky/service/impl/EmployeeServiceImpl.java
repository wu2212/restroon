package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.interceptor.JwtTokenAdminInterceptor;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import com.sky.utils.Tomd5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    //md5加密
    @Autowired
    private Tomd5 tomd5;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        String md5password = tomd5.toMD5(password);
        if (!md5password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    /**
     * 添加员工
     *
     * @param
     * @return
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        System.out.println(BaseContext.getCurrentId());
        employeeMapper.save(employeeDTO);
    }

    /**
     * 分页查询员工信息
     * @param pageinfo
     * @return
     */
    @Override
    public PageResult selectEmployeePage(EmployeePageQueryDTO pageinfo) {
        // 创建一个Map来存储分页参数和查询条件
       /* Map<String, Object> params = new HashMap<>();
        params.put("pageSize", pageinfo.getPageSize());
        params.put("pageNumber", pageinfo.getPage());
        params.put("name",pageinfo.getName());*/

        // 使用PageHelper.startPage方法的重载版本，传递分页参数和查询条件
        PageHelper.startPage(pageinfo.getPage(), pageinfo.getPageSize());

        /*List<EmployeeDTO> employeeDTOList = employeeMapper.selectEmployeeByName(pageinfo);
        PageResult PageInfo = new PageResult();
        PageInfo.setTotal(employeeDTOList.size());
        PageInfo.setRecords(employeeDTOList);*/
        Page page = employeeMapper.selectEmployeeByName(pageinfo);
        PageResult PageInfo = new PageResult();
        PageInfo.setTotal(page.getTotal());
        PageInfo.setRecords(page.getResult());

        return PageInfo;
    }

    /**
     * 修改员工状态
     * @param status
     * @param id
     */
    @Override
    public void StartorStop(Integer status, Long id) {

        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Override
    public Employee selectById(Long id) {
        return employeeMapper.selectById(id);
    }

    /**
     * 修改员工信息
     * @param employee
     */
    @Override
    public void updateEmployee(Employee employee) {
        employeeMapper.update(employee);
    }


}
