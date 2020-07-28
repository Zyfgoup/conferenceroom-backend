package com.zyfgoup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zyfgoup
 * @since 2020-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "e_id",type=IdType.ID_WORKER_STR)
    private String eId;

    /**
     * 工号
     */
    private String eNo;

    private String eName;

    private String ePassword;

    private String depId;

    private String role;


}
