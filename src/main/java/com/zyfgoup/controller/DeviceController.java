package com.zyfgoup.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyfgoup.common.lang.Result;
import com.zyfgoup.entity.Device;
import com.zyfgoup.service.DeviceService;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.Logical;
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
 * @since 2020-06-29
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;


    @PostMapping("/add")
    @RequiresRoles("admin")
    public Result addOrUpdate(@RequestBody Device device){

        Device device1 = deviceService.getOne(new QueryWrapper<Device>()
                .eq("dname",device.getDname())
                .eq("room_id",device.getRoomId()));

        //如果是修改的时候 是可以判断出存在的 还要判断id
        if(device1!=null && device.getDid()==null){
            return Result.fail("该会议室已存在此设备,请直接修改设备数量即可");
        }

        deviceService.saveOrUpdate(device);
        return Result.succ("");
    }

    @DeleteMapping("/delete/{did}")
    @RequiresRoles("admin")
    public Result delete(@PathVariable("did") Integer did){

        return Result.succ(deviceService.removeById(did));

    }


    /**
     * 根据会议室查询所有的设备
     * @param roomId
     * @return
     */
    @GetMapping("/listby/{roomId}")
    @RequiresRoles("admin")
    public Result listByRoomId(@PathVariable("roomId") Integer roomId){

        return Result.succ(deviceService.list(new QueryWrapper<Device>().eq("room_id",roomId).orderByDesc("dnumber")));

    }

    /**
     * 部门用户或者管理申请 只看数量大于等于1的
     * @param roomId
     * @return
     */

    @GetMapping("/listbyapply/{roomId}")
    @RequiresRoles(value={"admin","user"},logical = Logical.OR)
    public Result listByRoomIdDep(@PathVariable("roomId") Integer roomId){

        return Result.succ(deviceService.list(new QueryWrapper<Device>().eq("room_id",roomId)
                .ge("dnumber",1)
                .orderByDesc("dnumber")));

    }

    /*
    修改设备的数量  put请求不能将参数带在路径后面
     */
    @PutMapping("/changenumber")
    @RequiresRoles("admin")
    public Result changeNumber(@RequestBody Map<String, Object> map){
        return Result.succ(deviceService.update(new UpdateWrapper<Device>().eq("did",map.get("did"))
        .set("dnumber",map.get("number"))));
    }

}
