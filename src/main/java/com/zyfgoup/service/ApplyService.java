package com.zyfgoup.service;

import com.zyfgoup.entity.Apply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyfgoup
 * @since 2020-06-18
 */
public interface ApplyService extends IService<Apply> {

    List<Integer> searchTimeConflict(Integer roomId, LocalDateTime startTime, LocalDateTime endTime);
}
