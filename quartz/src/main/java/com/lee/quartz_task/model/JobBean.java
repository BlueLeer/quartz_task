package com.lee.quartz_task.model;

import com.lee.quartz_task.common.util.RTException;
import com.lee.quartz_task.common.util.ScheduleUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author WangLe
 * @date 2018/12/11 14:25
 * @description
 */
public class JobBean extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(JobBean.class);
    // 线程池对象
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobEntity jobEntity = (JobEntity) jobExecutionContext.getMergedJobDataMap().get(ScheduleUtils.JOB_KEY);
        try {
            logger.info("-----开始执行定时任务-----");
            Long startTime = System.currentTimeMillis();
            // 将从参数中获取到的任务对象,封装成runnable对象
            JobRunnable jobRunnable = new JobRunnable(jobEntity.getBeanName(), jobEntity.getMethodName(), jobEntity.getParams());

            // 提交任务
            Future<?> future = executorService.submit(jobRunnable);
            // 阻塞线程,直到done的状态为true
            future.get();
            Long endTime = System.currentTimeMillis();
            logger.info("任务执行完毕,任务ID: " + jobEntity.getJobId() + "  总共耗时: " + (endTime - startTime) + "毫秒");
        } catch (Exception e) {
            logger.info("任务执行失败!");
            throw new RTException("执行定时任务失败!");
        }
    }


    /*
    关于Future的说明:
        在并发编程时，一般使用runnable，然后扔给线程池完事，这种情况下不需要线程的结果。
        所以run的返回值是void类型。
        如果是一个多线程协作程序，比如菲波拉切数列，1，1，2，3，5，8...使用多线程来计算。
        但后者需要前者的结果，就需要用callable接口了。
        callable用法和runnable一样，只不过调用的是call方法，该方法有一个泛型返回值类型，你可以任意指定。
        线程是属于异步计算模型，所以你不可能直接从别的线程中得到函数返回值。
        这时候，Future就出场了。Future可以监视目标线程调用call的情况，当你调用Future的get()方法以获得结果时，
        当前线程就开始阻塞，直接call方法结束返回结果。
     */
}
