package com.zyfgoup.service.impl;

import com.zyfgoup.entity.Apply;
import com.zyfgoup.mapper.ApplyMapper;
import com.zyfgoup.service.ApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyfgoup
 * @since 2020-06-18
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {
    @Autowired
    ApplyMapper applyMapper;


    @Override
    public List<Integer> searchTimeConflict(Integer roomId, LocalDateTime startTime, LocalDateTime endTime) {
        return applyMapper.searchTimeConflict(roomId,startTime,endTime);
    }
}
