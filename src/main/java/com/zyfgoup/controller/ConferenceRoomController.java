package com.zyfgoup.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyfgoup.common.dto.Conditions;
import com.zyfgoup.common.lang.Result;
import com.zyfgoup.entity.ConferenceRoom;
import com.zyfgoup.service.ConferenceRoomService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 会议室模块的 包括管理员管理会议室 部门申请会议室时的会议室展示
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zyfgoup
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/conference-room")
public class ConferenceRoomController {

    @Autowired
    ConferenceRoomService conferenceRoomService;


    /**
     * 修改或者添加会议室  传入的conference对象如果id存在数据库中 则是修改
     * @param conferenceRoom
     * @return
     */
    @PostMapping("/add")
    @RequiresRoles("admin")
    public Result saveorupdate(@RequestBody ConferenceRoom conferenceRoom){
        if(conferenceRoom.getRoomId()==null|| conferenceRoom.getRoomId()==0){
            //新添加的
            //要看楼层与编号是否有重复的
            ConferenceRoom one = conferenceRoomService.getOne(new QueryWrapper<ConferenceRoom>()
                    .eq("room_no", conferenceRoom.getRoomNo())
                    .eq("room_floor", conferenceRoom.getRoomFloor()));

            if(one!=null){
                //该楼层已有该编号的会议室 不能添加
                return Result.fail("楼层已有该编号会议室，添加失败");
            }

            //如果没有重复 则设置一下状态为可用

            conferenceRoom.setRoomState(1);
        }

        conferenceRoomService.saveOrUpdate(conferenceRoom);
        return Result.succ("");

    }


    /**
     * 管理员 修改会议室的可用/维修状态
     * 前台直接返回一行的数据 element的开关的时间返回的 其实就是一个ComferenceRoom的对象
     * 但是注意每个属性的名称与实体类的属性名一致
     * @param
     * @return
     */
    @PutMapping("/changestate")
    @RequiresRoles("admin")
    public Result changeState(@RequestBody ConferenceRoom conferenceRoom){


      boolean b = conferenceRoomService.update
              (new UpdateWrapper<ConferenceRoom>()
                      .set("room_state",conferenceRoom.getRoomState())
                      .eq("room_id",conferenceRoom.getRoomId()));
        if(b){
            return Result.succ("");
        }else{
            return Result.fail("修改失败");
        }

    }

    /**
     * 列出所有的会议室  角色注意是or 默认是都要满足
     * @return
     */
    @GetMapping("/listall")
    @RequiresRoles("admin")
    public Result listAll(){
        return Result.succ(conferenceRoomService.list());
    }


    /**@PathVariable(value = "roomFloor",required = false) 设置参数可以为空无效
     * 在使用swagger2测试接口时 传入的参数已经是字符串了  不要再加""
     *
     * @param roomFloor
     * @param roomType
     * @param roomSize
     * @return
     */
    @GetMapping("/listby/{roomFloor}/{roomType}/{" +
            "roomSize}")
    @RequiresRoles(value ="admin")
    public Result listAll(@PathVariable(value = "roomFloor")  String roomFloor,
                          @PathVariable(value = "roomType")  String roomType,
                          @PathVariable(value = "roomSize")  String roomSize
                          ) throws UnsupportedEncodingException {

            roomFloor  = String.valueOf(JSON.parse(roomFloor));
            roomType =(String)JSON.parse(roomType);

            //前端是number 但是使用JSON转成json格式 用String接收
            roomSize = String.valueOf(JSON.parse(roomSize));
        System.out.println(roomFloor+","+roomType+","+roomSize);

        List<ConferenceRoom> list = conferenceRoomService.list(new QueryWrapper<ConferenceRoom>()

                //第一个参数为true则拼接  这里为null或者""则返回true
                .eq(!StrUtil.hasBlank(roomType),"room_type", roomType)
                .eq(!StrUtil.hasBlank(roomFloor),"room_floor", roomFloor)


                //如果字符串为空不好转int
                .eq(!StrUtil.hasBlank(roomSize),"room_size",
                        StrUtil.hasBlank(roomSize)?0:Integer.valueOf(roomSize))
        );



        return Result.succ(list);
    }


    /**
     * 查出所有楼层、类型、容纳人数  供页面条件筛选会议室
     * @return
     */
    @GetMapping("/getconditions")
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    public Result getConditions(){

        List<Map<String,Integer>> floors = new ArrayList<>();
        List<Map<String,String>> types = new ArrayList<>();
        List<Map<String,Integer>> sizes = new ArrayList<>();



        List<ConferenceRoom>  list1 = conferenceRoomService.list(
                new QueryWrapper<ConferenceRoom>()
                        .select("distinct room_floor"));


        List<Integer> list = new ArrayList();
        for(ConferenceRoom conferenceRoom:list1){
            list.add(Integer.valueOf(conferenceRoom.getRoomFloor()));
        }
        //对楼层进行排序
       Collections.sort(list);

        for(Integer i:list){
            Map<String,Integer> map = new HashMap();
            map.put("roomFloor",i);
            floors.add(map);
        }


        List<ConferenceRoom>  list2 = conferenceRoomService.list(
                new QueryWrapper<ConferenceRoom>()
                        .select("distinct room_type"));
        for(ConferenceRoom conferenceRoom:list2){
            Map<String,String> map = new HashMap();
            map.put("roomType",conferenceRoom.getRoomType());
           types.add(map);
        }

        List<ConferenceRoom>  list3 = conferenceRoomService.list(
                new QueryWrapper<ConferenceRoom>()
                        .select("distinct room_size").orderByAsc("room_size"));
        for(ConferenceRoom conferenceRoom:list3){

            Map<String,Integer> map = new HashMap();
            map.put("roomSize",conferenceRoom.getRoomSize());
            sizes.add(map);
        }

        return Result.succ(new Conditions(floors,types,sizes));
    }


    @DeleteMapping("/delete/{id}")
    @RequiresRoles("admin")
    public Result delete(@PathVariable("id") int id){
        conferenceRoomService.removeById(id);

        return Result.succ("");
    }



    /**获取所有条件
     * 申请会议室时根据条件筛选 但是与管理员管理会议室不同 这里只能搜索到可用状态为1的情况
     * @return
     */
    @GetMapping("/getconditionsonstate")
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    public Result getConditionsOnState(){

        List<Map<String,Integer>> floors = new ArrayList<>();
        List<Map<String,String>> types = new ArrayList<>();
        List<Map<String,Integer>> sizes = new ArrayList<>();



        List<ConferenceRoom>  list1 = conferenceRoomService.list(
                new QueryWrapper<ConferenceRoom>().eq("room_state",1)
                        .select("distinct room_floor"));


        List<Integer> list = new ArrayList();
        for(ConferenceRoom conferenceRoom:list1){
            list.add(Integer.valueOf(conferenceRoom.getRoomFloor()));
        }
        //对楼层进行排序
        Collections.sort(list);

        for(Integer i:list){
            Map<String,Integer> map = new HashMap();
            map.put("roomFloor",i);
            floors.add(map);
        }


        List<ConferenceRoom>  list2 = conferenceRoomService.list(
                new QueryWrapper<ConferenceRoom>().eq("room_state",1)
                        .select("distinct room_type"));
        for(ConferenceRoom conferenceRoom:list2){
            Map<String,String> map = new HashMap();
            map.put("roomType",conferenceRoom.getRoomType());
            types.add(map);
        }

        List<ConferenceRoom>  list3 = conferenceRoomService.list(
                new QueryWrapper<ConferenceRoom>().eq("room_state",1)
                        .select("distinct room_size").orderByAsc("room_size"));
        for(ConferenceRoom conferenceRoom:list3){

            Map<String,Integer> map = new HashMap();
            map.put("roomSize",conferenceRoom.getRoomSize());
            sizes.add(map);
        }

        return Result.succ(new Conditions(floors,types,sizes));
    }


    /**
     * 获取所有会议室  但是都是可用状态为1的
     * @return
     */
    @GetMapping("/listallonstate")
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    public Result listAllOnState(){
        return Result.succ(conferenceRoomService.list(new QueryWrapper<ConferenceRoom>().eq("room_state",1)));
    }


    /**
     * 根据条件筛选会议室 但是都是状态为1的情况下
     * @param roomFloor
     * @param roomType
     * @param roomSize
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/listbyonstate/{roomFloor}/{roomType}/{" +
            "roomSize}")
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    public Result listAllOnState(@PathVariable(value = "roomFloor")  String roomFloor,
                          @PathVariable(value = "roomType")  String roomType,
                          @PathVariable(value = "roomSize")  String roomSize
    )  {

        roomFloor  = String.valueOf(JSON.parse(roomFloor));
        roomType =(String)JSON.parse(roomType);

        //前端是number 但是使用JSON转成json格式 用String接收
        roomSize = String.valueOf(JSON.parse(roomSize));

        List<ConferenceRoom> list = conferenceRoomService.list(new QueryWrapper<ConferenceRoom>()

                //第一个参数为true则拼接  这里为null或者""则返回true
                .eq(!StrUtil.hasBlank(roomType),"room_type", roomType)
                .eq(!StrUtil.hasBlank(roomFloor),"room_floor", roomFloor)


                //如果字符串为空不好转int
                .eq(!StrUtil.hasBlank(roomSize),"room_size",
                        StrUtil.hasBlank(roomSize)?0:Integer.valueOf(roomSize))
                .eq("room_state",1)
        );



        return Result.succ(list);
    }


}
