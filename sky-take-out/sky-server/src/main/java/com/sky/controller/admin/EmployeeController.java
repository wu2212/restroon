package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     *
     * @return
     */
    @PostMapping()
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.save(employeeDTO);
        return Result.success("添加成功");
    }

    /**
     *分页查询
     */
    @GetMapping("/page")
    public Result<PageResult> selectUserPage(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer page,
            @RequestParam(value = "name", required = false) String name)
    {
        EmployeePageQueryDTO pageinfo = new EmployeePageQueryDTO();
        pageinfo.setPageSize(pageSize);
        pageinfo.setPage(page);
        pageinfo.setName(name);

        PageResult pageResult = employeeService.selectEmployeePage(pageinfo);
        return Result.success(pageResult);
    }

    /**
     * 修改员工状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result StartorStop (@PathVariable Integer status,Long id){
        employeeService.StartorStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> selectById(@PathVariable Long id){
        Employee employee = employeeService.selectById(id);
        return Result.success(employee);

    }

    /**
     * 修改员工信息
     * @param employee
     * @return
     */
    @PutMapping()
    public Result updateEmployee(@RequestBody Employee employee){
        employeeService.updateEmployee(employee);
        return Result.success();
    }
}
