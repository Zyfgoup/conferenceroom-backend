package com.zyfgoup.common.dto;

import com.zyfgoup.entity.Apply;
import com.zyfgoup.entity.ConferenceRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 申请会议室时前后端交互的数据载体类
 * @Author Zyfgoup
 * @Date 2020/6/24 0:46
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    private Apply apply;
    private ConferenceRecord conferenceRecord;

}
