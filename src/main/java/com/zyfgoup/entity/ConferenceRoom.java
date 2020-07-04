package com.zyfgoup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zyfgoup
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConferenceRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "room_id", type = IdType.AUTO)
    private Integer roomId;

    /**
     * 会议室编号
     */
    private String roomNo;

    /**
     * 会议室楼层
     */
    private String roomFloor;

    /**
     * 会议室类型
     */
    private String roomType;

    /**
     * 会议室容纳人数
     */
    private Integer roomSize;

    /**
     * 会议室状态 0表示维修 1表示在可用
     */
    private Integer roomState;


}
