package com.zyfgoup.service;

import com.zyfgoup.common.dto.Record;
import com.zyfgoup.entity.ConferenceRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyfgoup.entity.group.ConRApplyRecord;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyfgoup
 * @since 2020-06-18
 */
public interface ConferenceRecordService extends IService<ConferenceRecord> {

    List<ConRApplyRecord> listByConditions(Integer auditState,
                                           String depName,Integer currentPage,Integer deleted);

    Integer getTotalByConditions( Integer auditState,
                                  String depName,Integer deleted);
}
