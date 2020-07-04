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
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "did", type = IdType.AUTO)
    private Integer did;

    /**
     * 名称
     */
    private String dname;

    /**
     * 设备数量
     */
    private Integer dnumber;

    /**
     * 会议室id
     */
    private Integer roomId;


}
