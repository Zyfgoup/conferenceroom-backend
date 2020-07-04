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
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键uuid
     */
    @TableId(value = "dep_id",type=IdType.ID_WORKER_STR)
    private String depId;

    /**
     * 部门账号作为登录账号
     */
    private String depNo;

    /**
     * 部门名称
     */
    private String depName;

    /**
     * 部门密码
     */
    private String depPassword;

    /**
     * 部门人数
     */
    private Integer depPersonCount;

    /**
     * 部门邮箱 一般账号掌控在一个人手里 通过提醒通知要开会
     */
    private String depEmail;

    /**
     * 角色是用户
     */
    private String role;


}
