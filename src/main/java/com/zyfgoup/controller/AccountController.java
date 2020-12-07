package com.zyfgoup.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyfgoup.common.dto.LoginDto;
import com.zyfgoup.common.lang.Result;

import com.zyfgoup.entity.Admin;
import com.zyfgoup.entity.Department;
import com.zyfgoup.entity.Employee;
import com.zyfgoup.service.AdminService;
import com.zyfgoup.service.DepartmentService;
import com.zyfgoup.service.EmployeeService;
import com.zyfgoup.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Zyfgoup
 * @Date 2020/6/18 14:35
 * @Description
 */
@RestController
public class AccountController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    AdminService adminService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JwtUtils jwtUtils;


    /**
     * 关闭浏览器后  重新获得用户信息给前台存入cookie session
     * 会先走JwtFilter 如果过期了 就抛出异常 返回响应了
     * 能走到这里表示还没过期
     * @param
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfo(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        Claims claimByToken = jwtUtils.getClaimByToken(jwt);
        //throw new TestException("模拟token");
        String id = claimByToken.getSubject();
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("id",id));
        Department department = departmentService.getOne(new QueryWrapper<Department>().eq("dep_id",id));
        Employee employee = employeeService.getOne(new QueryWrapper<Employee>().eq("e_id",id));
        if(admin!=null) {
            return Result.succ(MapUtil.builder()
                    .put("id", admin.getId())
                    .put("username", "管理员")
                    .put("role", admin.getRole())
                    .map()
            );
        }else if(department!=null){
            return Result.succ(MapUtil.builder()
                    //注意这里将long型id字符串 不然前台获取会四舍五入
                    .put("id",department.getDepId())
                    .put("username",department.getDepName())
                    .put ("role",department.getRole())
                    .map()
            );
        }else{
            return Result.succ(MapUtil.builder()
                    .put("id", employee.getEId())
                    .put("username", employee.getEName())
                    .put("role", employee.getRole())
                    .put("depId", employee.getDepId())
                    .map()
            );
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto,HttpServletResponse response){
        Department department = departmentService.getOne(new QueryWrapper<Department>().eq("dep_no",loginDto.getUsername()));


        //部门为空
        if(department==null){

            //判断员工
            Employee employee = employeeService.getOne(new QueryWrapper<Employee>().eq("e_no",loginDto.getUsername()));

            //管理员是否为空
            if(employee == null){
                //判断是否为管理员
                Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("username", loginDto.getUsername()));

                if(admin == null){
                    //为空则抛出异常 那么全局异常会捕获
                    return Result.fail("用户不存在");
                }else{

                    //管理员存在 判断密码
                    if(!admin.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
                        //密码不正确
                        return Result.fail("密码不正确");
                    }else{
                        //密码也正确
                        String jwt = jwtUtils.generateToken(admin.getId());
                        response.setHeader("Authorization", jwt);
                        response.setHeader("Access-control-Expose-Headers", "Authorization");
                        return Result.succ(MapUtil.builder()
                                .put("id",admin.getId())
                                .put("username","管理员")
                                .put ("role",admin.getRole())
                                .map()
                        );

                    }
                }

            }else {

                //员工存在 判断密码
                if (!employee.getEPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
                    //密码不正确
                    return Result.fail("密码不正确");
                } else {
                    //密码也正确
                    String jwt = jwtUtils.generateToken(Long.valueOf(employee.getEId()));
                    response.setHeader("Authorization", jwt);
                    response.setHeader("Access-control-Expose-Headers", "Authorization");
                    return Result.succ(MapUtil.builder()
                            .put("id", employee.getEId())
                            .put("username", employee.getEName())
                            .put("role", employee.getRole())
                            .put("depId", employee.getDepId())
                            .map()
                    );

                }
            }

        }else{
            //部门不为空
            if(!department.getDepPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
                //密码错误
                return Result.fail("密码不正确");
            }else{
                String jwt = jwtUtils.generateToken(Long.valueOf(department.getDepId()));
                response.setHeader("Authorization", jwt);
                response.setHeader("Access-control-Expose-Headers", "Authorization");
                return Result.succ(MapUtil.builder()
                        //注意这里将long型id字符串 不然前台获取会四舍五入
                        .put("id",department.getDepId())
                        .put("username",department.getDepName())
                        .put ("role",department.getRole())
                        .map()
                );
            }
        }
    }


    @RequiresAuthentication
    @GetMapping("logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ("退出成功");
    }
}
