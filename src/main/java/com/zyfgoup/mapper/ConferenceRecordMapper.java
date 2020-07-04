package com.zyfgoup.mapper;

import com.zyfgoup.common.dto.Record;
import com.zyfgoup.entity.ConferenceRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyfgoup.entity.group.ConRApplyRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyfgoup
 * @since 2020-06-18
 */

@Repository
public interface ConferenceRecordMapper extends BaseMapper<ConferenceRecord> {

     List<ConRApplyRecord> listByConditions(@Param("auditState") Integer auditState,
                                            @Param("depName") String depName,
                                            @Param("startIndex") Integer startIndex,
                                            @Param("deleted") Integer deleted);

     Integer getTotalByConditions(@Param("auditState") Integer auditState,
                                  @Param("depName") String depName,@Param("deleted") Integer deleted);
}
