package com.lee.quartz_task.common.util;

import com.lee.quartz_task.model.JobBean;
import com.lee.quartz_task.model.JobEntity;
import org.quartz.*;

/**
 * @author WangLe
 * @date 2018/12/11 14:10
 * @description 定时任务工具类
 */
public class ScheduleUtils {
    // 定时任务名称的前缀
    public final static String JOB_NAME_PRE = "TASK_";

    // 任务key
    public final static String JOB_KEY = "JOB_KEY";

    /**
     * 获取触发器key
     *
     * @param jobId
     * @return
     */
    private static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME_PRE + jobId);
    }

    /**
     * 获取任务key
     *
     * @param jobId
     * @return
     */
    private static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME_PRE + jobId);
    }

    /**
     * 获取表达式触发器
     *
     * @param scheduler
     * @param jobId
     * @return
     */
    private static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new RTException("获取定时任务CronTrigger出现异常!");
        }
    }

    /**
     * 创建定时任务
     */
    public static void createJob(Scheduler scheduler, JobEntity jobEntity) {
        // 构建job信息
        JobDetail jobDetail = JobBuilder
                .newJob(JobBean.class)
                .withIdentity(getJobKey(jobEntity.getJobId()))
                .build();

        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(jobEntity.getCronExpression())
                .withMisfireHandlingInstructionDoNothing(); //所有的misfire不管，执行下一个周期的任务,也就是说下个周期再执行

        // 构建一个trigger
        CronTrigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(getTriggerKey(jobEntity.getJobId()))
                .withSchedule(scheduleBuilder)
                .build();

        // 放入参数,运行时的方法可以获取
        jobDetail.getJobDataMap().put(JOB_KEY, jobEntity);

        // 交给调度器执行
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateJob(Scheduler scheduler, JobEntity jobEntity) {
        TriggerKey triggerKey = getTriggerKey(jobEntity.getJobId());

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobEntity.getCronExpression()).withMisfireHandlingInstructionDoNothing();

        // 获取原来的trigger
        CronTrigger cronTrigger = getCronTrigger(scheduler, jobEntity.getJobId());

        // 任务更新以后,可能trigger表达式也会发生变化,所以这里需要重新构建
        cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

        // 放入参数
        cronTrigger.getJobDataMap().put(JOB_KEY, jobEntity);

        //
        try {
            scheduler.rescheduleJob(triggerKey, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RTException("更新定时任务失败!");
        }

    }

    /**
     * 立即执行任务
     *
     * @param scheduler
     * @param jobEntity
     */
    public static void runNow(Scheduler scheduler, JobEntity jobEntity) {
        JobDataMap map = new JobDataMap();
        map.put(JOB_KEY, jobEntity);

        try {
            scheduler.triggerJob(getJobKey(jobEntity.getJobId()), map);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RTException("立即执行任务失败!");
        }
    }

    /**
     * 暂停任务
     *
     * @param scheduler
     * @param jobEntity
     */
    public static void pause(Scheduler scheduler, JobEntity jobEntity) {
        try {
            scheduler.pauseJob(getJobKey(jobEntity.getJobId()));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RTException("暂停任务失败!");
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param jobEntity
     */
    public static void resume(Scheduler scheduler, JobEntity jobEntity) {
        try {
            scheduler.resumeJob(getJobKey(jobEntity.getJobId()));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RTException("恢复任务失败");
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler
     * @param jobId
     */
    public static void deleteJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RTException("删除定时任务失败!");
        }
    }
}
