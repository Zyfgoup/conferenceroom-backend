package com.zyfgoup.controller;


import cn.hutool.core.net.URLDecoder;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyfgoup.common.lang.Result;
import com.zyfgoup.entity.Admin;
import com.zyfgoup.entity.Department;
import com.zyfgoup.service.DepartmentService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zyfgoup
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;



    /**
        因为longid到前端会四舍五入了 所以通过depNo更新了
     */
    @PostMapping("/update")
    @RequiresRoles("admin")
    public Result update(@RequestBody Department department){
        //新密码加密
        department.setDepPassword(SecureUtil.md5(department.getDepPassword()));
        departmentService.update(department,new UpdateWrapper<Department>().eq("dep_id",department.getDepId()));
        return Result.succ("");
    }


    /**
     添加部门
     */
    @PostMapping("/add")
    @RequiresRoles("admin")
    public Result save(@RequestBody Department department){


            //密码md5加密
            department.setDepPassword(SecureUtil.md5(department.getDepPassword()));

            departmentService.save(department);

        //因为设置了账号与名称唯一 如果有重复数据库会报错  我在全局异常处理捕获了

            return Result.succ("操作成功");

    }


    /**
     * delete 由于部门有做别的表的外键 所以设置外键时设置到级联删除
     * 最新更新  将department的id改为varchar 不然太多前后端交互的id值精度丢失
     * 但是这里还是用之前的根据dep_no来删除 懒得更新了
     */


    @DeleteMapping("/delete/{depNo}")
    @RequiresRoles("admin")
    public Result delete(@PathVariable("depNo") String depNo){
        boolean b = departmentService.remove(new QueryWrapper<Department>().eq("dep_no",depNo));
        if(b){
            return Result.succ("操作成功");
        }else{
            return Result.fail("删除失败");
        }
    }


    /**
     * 查询所有部门  由于id是long型 给前端后四舍五入 所以在前端进行改删等操作时 用depNo来查就好了
     * 方便  懒得去重新改成id是varchar类型了
     * @return
     */
    @GetMapping("/listAll")
    @RequiresRoles("admin")
    public Result listAll(){
        List<Department> list = departmentService.list();
        return Result.succ(list);
    }


    /**
     * 给用户修改密码  需要提交旧密码验证通过才能修改
     * map
     * {"id":"","oldPassword":"","newPassword":""}
     * @param info
     * @return
     */
    @PutMapping("/updatepwd")
    @RequiresRoles("user")
    public Result updatePwd(@RequestBody Map<String,Object> info){

        System.out.println(info);

        //md5加密
        String oldPassword = (String)info.get("oldPassword");
        oldPassword = SecureUtil.md5(oldPassword);



        //注意 在登录的时候返回给前端的数据里id转成String了 不然精度丢失
        //所以这里idy要转回long

        //最新更新  将department的id改为varchar 不然太多前后端交互的id值精度丢失
        Department one = departmentService.getOne(new QueryWrapper<Department>()
                .eq("dep_id", info.get("id"))
                .eq("dep_password", oldPassword));
        if(one !=null){


            //加密
            String newPassword = (String)info.get("newPassword");
            newPassword = SecureUtil.md5(newPassword);

            departmentService.update(new UpdateWrapper<Department>()
                    .eq("dep_id",info.get("id"))
                    .set("dep_password",newPassword));

            return Result.succ("修改成功");
        }
        return Result.fail("修改失败，密码不正确");

    }

    @GetMapping("/getby/{id}")
    @RequiresRoles("user")
    public Result getById(@PathVariable("id") String depId){
        Department byId = departmentService.getById(depId);
        return Result.succ(byId);
    }


    /*
    提供给普通用户修改部门邮箱
     */
    @PutMapping("/updateemail")
    @RequiresRoles("user")
    public Result updateEmail(@RequestBody Map<String,Object> info){
        //md5加密
        String oldPassword = (String)info.get("password");
        oldPassword = SecureUtil.md5(oldPassword);
        Department one = departmentService.getOne(new QueryWrapper<Department>()
                .eq("dep_id", info.get("id"))
                .eq("dep_password", oldPassword));
        if(one !=null){
            departmentService.update(new UpdateWrapper<Department>()
                    .eq("dep_id",info.get("id"))
                    .set("dep_email",info.get("newEmail")));
            return Result.succ("修改成功");
        }
        return Result.fail("修改失败，密码不正确");
    }

    @GetMapping("/getalldeps")
    @RequiresRoles("admin")
    public Result getAllDeps(){
       List<Department> list =  departmentService.list();
       List<Map<String,String>> list1 = new ArrayList<>();
       for(Department department:list){
           Map<String, String> map = new HashMap<>();
           map.put("depName",department.getDepName());
           map.put("depId",department.getDepId());
           list1.add(map);
       }

       return Result.succ(list1);
    }

}
