package com.zyfgoup.service.impl;

import com.zyfgoup.service.QuartzServie;
import com.zyfgoup.util.Date2Cron;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/6/26 15:45
 * @Description
 */

@Service
public class QuartzServiceImpl implements QuartzServie {
    @Autowired
    private Scheduler scheduler;
    // job组和trigger组默认的名字
    private static String JOB_GROUP_NAME = "MY_JOB_GROUP";
    private static String TRIGGER_GROUP_NAME = "MY_TRIGGER_GROUP";

    @Override
    public void startJob(long time, String jobName, Class job, Map<String,Object> info) {
        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(jobName, JOB_GROUP_NAME).build();//设置Job的名字和组

           Date time1 = new Date(time);


            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(Date2Cron.getCron(time1));
            //设置定时任务的时间触发规则
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(scheduleBuilder).build();


            //传递参数
            jobDetail.getJobDataMap().put("info", info);

        // 把作业和触发器注册到任务调度中, 启动调度
            scheduler.scheduleJob(jobDetail, cronTrigger);

        // 启动调度
         scheduler.start();
         //Thread.sleep(30000);
        // 停止调度
        //scheduler.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseJob(String triggerName, String triggerGroupName) {

    }

    @Override
    public void resumeJob(String triggerName, String triggerGroupName) {

    }

    @Override
    public void deleteJob(String triggerName, String triggerGroupName) {

    }

    @Override
    public void doJob(String triggerName, String triggerGroupName) {

    }

    @Override
    public void startAllJob() {

    }

    @Override
    public void shutdown() {

    }
}
