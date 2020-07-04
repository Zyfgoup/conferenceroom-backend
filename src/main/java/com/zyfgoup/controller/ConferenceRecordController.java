package com.zyfgoup.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyfgoup.common.dto.Conditions;
import com.zyfgoup.common.lang.Result;
import com.zyfgoup.entity.Apply;
import com.zyfgoup.entity.ConferenceRoom;
import com.zyfgoup.entity.group.ConRApplyRecord;
import com.zyfgoup.quartz.EmailJobDetail;
import com.zyfgoup.service.*;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * 会议室申请记录的api 供管理员、部门查询会议室的申请记录  对应到ConRApplyRecord实体类
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zyfgoup
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/record")
public class ConferenceRecordController {

    @Autowired
    ConferenceRecordService  conferenceRecordService;

    @Autowired
    ApplyService applyService;

    @Autowired
    QuartzServie quartzServie;


    /**
     * 根据审核的状态 和部门来查record记录  分页
     * 如果是部门自己查看的话 那么depName就固定了  按照申请时间降序
     * @param auditState
     * @param depName
     * @return
     */

    @GetMapping("listbyconditions/{auditState}/{depName}/{currentPage}/{deleted}")
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    public Result listByConditions(@PathVariable("auditState") String auditState,
                                   @PathVariable("depName") String depName,
                                    @PathVariable("currentPage") Integer currentPage,
     @PathVariable("deleted") String deleted){

        String name = (String)JSON.parse(depName);
    Integer state = Integer.valueOf(auditState);

    //因为有可能管理员查的时候传的是空的 所以要用JSON转
    Integer deleted1 = (Integer)JSON.parse(deleted);

        //这里传入当前页数 到了service层转化为记录的开始下标给mapper
       return Result.succ( conferenceRecordService.listByConditions(state,name,currentPage,deleted1));
    }


    /*
    审核申请  审核的同时如果是通过则要检测是否时间冲突
    {
       applyId:
       roomId:
       startTime:
       endTime:
       auditState:
       rejectReason:

        发邮件要用
       roomFloor
       roomNo
       applyTime
       depEmail
       theme

    }
     */
    @PutMapping("/changeauditstate")
    @RequiresRoles("admin")
    public Result changeAuditState(@RequestBody Map<String,Object> map) throws ParseException {


        if((Integer)map.get("auditState")==1) {

            //判断开始时间是否早于现在 如果早于 那么不能通过
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
            Date date = simpleDateFormat.parse((String)map.get("startTime"));
             if(System.currentTimeMillis()>date.getTime()){
                    return Result.fail("无法通过,开始时间已过,请驳回");
             }


            DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse((String) map.get("startTime"), DATETIME_FORMATTER);
            LocalDateTime endTime = LocalDateTime.parse((String) map.get("endTime"), DATETIME_FORMATTER);
            List<Integer> list = applyService.searchTimeConflict((Integer) map.get("roomId"), startTime, endTime);
            if (list.size() > 0) {
                return Result.fail("无法通过,时间已冲突,请驳回请求并说明理由");
            } else {

                //修改状态  通过
                applyService.update(new UpdateWrapper<Apply>()
                        .eq("apply_id", map.get("applyId"))
                        .set("audit_state", map.get("auditState")));

                //发一封通过的
                Long time = System.currentTimeMillis();
                time += 30*1000;//30秒后发
                quartzServie.startJob(time,UUID.randomUUID().toString(),EmailJobDetail.class,map);

                //然后发一个开始前15分钟的提醒开会的
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
                Date date1 = simpleDateFormat1.parse((String)map.get("startTime"));
                long time2 = date1.getTime()-(15*60*1000);

              Map<String,Object> map1 = new HashMap<>();
              map1.put("theme",map.get("theme"));
              map1.put("depEmail",map.get("depEmail"));
              map1.put("roomFloor",map.get("roomFloor"));
                map1.put("roomNo",map.get("roomNo"));
                map1.put("startTime",map.get("startTime"));
                map1.put("endTime",map.get("endTime"));
                //System.out.println(map1);

                quartzServie.startJob(time2,UUID.randomUUID().toString(),EmailJobDetail.class,map1);
                return Result.succ("");
            }
        }else{
            //修改状态
            applyService.update(new UpdateWrapper<Apply>()
                    .eq("apply_id", map.get("applyId"))
                    .set("audit_state", map.get("auditState"))
            .set("reject_reason",map.get("rejectReason")));

            Long time = System.currentTimeMillis();
            time += 30*1000;//30秒后发
            quartzServie.startJob(time,UUID.randomUUID().toString(),EmailJobDetail.class,map);

            //发一个未通过的邮件
            return Result.succ("");
        }


    }

    /**
     * 撤销申请
     * @param map
     * @return
     */

    @PutMapping("/recallapply")
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    public Result recallApply(@RequestBody Map<String,Object> map){

        //删除了这个 conference_record表里的外键为applyId=这个的也会删除  级联
        applyService.removeById((Integer)map.get("applyId"));

        return  Result.succ("");
    }


    /**
     * 返回总数
     * @param auditState
     * @param depName
     * @return
     */
    @GetMapping("gettotalbyconditions/{auditState}/{depName}/{deleted}")
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    public Result getTotalByConditions(@PathVariable("auditState") String auditState,
                                   @PathVariable("depName") String depName,@PathVariable("deleted") String deleted){
        String name = (String)JSON.parse(depName);
        Integer deleted1 = (Integer)JSON.parse(deleted);
        return Result.succ(conferenceRecordService.getTotalByConditions(Integer.valueOf(auditState),name,deleted1));
    }


    /*
        供用户删除 不是真的删除 只是对用户不可见
     */
    @DeleteMapping("/delete/{applyId}")
    @RequiresRoles("user")
    public Result deleteByIdUser(@PathVariable("applyId") Integer applyId){

        applyService.update(new UpdateWrapper<Apply>().eq("apply_id",applyId)
        .set("deleted",1)
        );
        return Result.succ("");
    }


    /**
     * 管理员删除记录 真的删除
     * @param applyId
     * @return
     */
    @DeleteMapping("/deleteby/{applyId}")
    @RequiresRoles("admin")
    public Result deleteByIdAdmin(@PathVariable("applyId") Integer applyId){

        applyService.removeById(applyId);
        //conferenceRecord表会被级联删除
        return Result.succ("");
    }



}
