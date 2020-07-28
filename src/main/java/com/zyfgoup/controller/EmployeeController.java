package com.zyfgoup.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyfgoup.common.dto.EmployeeInfo;
import com.zyfgoup.common.lang.Result;
import com.zyfgoup.entity.Department;
import com.zyfgoup.entity.Device;
import com.zyfgoup.entity.Employee;
import com.zyfgoup.service.DepartmentService;
import com.zyfgoup.service.EmployeeService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zyfgoup
 * @since 2020-07-28
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("get/{eId}")
    @RequiresRoles("user")
    public Result getById(@PathVariable("eId") String eId){
        return Result.succ(employeeService.getById(eId));
    }


    /**
     * 获取信息
     * @param eId
     * @return
     */
    @GetMapping("getInfo/{eId}")
    @RequiresRoles("user")
    public Result getInfo(@PathVariable("eId") String eId){
        Employee employee = employeeService.getById(eId);
        Department department = departmentService.getById(employee.getDepId());
        return Result.succ(new EmployeeInfo(employee,department));
    }


    /**
     * 修改密码
     */
    @PutMapping("/updatepwd")
    @RequiresRoles("user")
    public Result updatePwd(@RequestBody Map<String,Object> info){
        //md5加密
        String oldPassword = (String)info.get("oldPassword");
        oldPassword = SecureUtil.md5(oldPassword);



        //注意 在登录的时候返回给前端的数据里id转成String了 不然精度丢失
        //所以这里idy要转回long

        //最新更新  将department的id改为varchar 不然太多前后端交互的id值精度丢失
        Employee one = employeeService.getOne(new QueryWrapper<Employee>()
                .eq("e_id", info.get("id"))
                .eq("e_password", oldPassword));
        if(one !=null){
            //加密
            String newPassword = (String)info.get("newPassword");
            newPassword = SecureUtil.md5(newPassword);

            employeeService.update(new UpdateWrapper<Employee>()
                    .eq("e_id",info.get("id"))
                    .set("e_password",newPassword));

            return Result.succ("修改成功");
        }
        return Result.fail("修改失败，密码不正确");
    }

    @GetMapping("getby/{depId}")
    @RequiresRoles("admin")
    public Result getByDepId(@PathVariable("depId") String depId){
        return Result.succ(employeeService.list(new QueryWrapper<Employee>().eq("dep_id",depId)));
    }

    @DeleteMapping("/delete/{eId}")
    @RequiresRoles("admin")
    public Result delete(@PathVariable("eId") String eId){

        return Result.succ(employeeService.removeById(eId));

    }

    @PostMapping("/add")
    @RequiresRoles("admin")
    public Result addOrUpdate(@RequestBody Employee employee){

        System.out.println(employee);

       Employee employee1 = employeeService.getOne(new QueryWrapper<Employee>()
                .eq("e_no",employee.getENo()));

        //如果是修改的时候 是可以判断出存在的 还要判断id
        if(employee1!=null && (employee.getEId()==null || employee.getEId().equals(""))){
            return Result.fail("该部门已存在此工号,请设置其他工号");
        }

        employee.setEPassword(SecureUtil.md5(employee.getEPassword()));

        employeeService.saveOrUpdate(employee);
        return Result.succ("");
    }

}
