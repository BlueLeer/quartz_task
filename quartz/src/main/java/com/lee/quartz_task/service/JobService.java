package com.lee.quartz_task.service;

import com.lee.quartz_task.model.JobEntity;

import java.util.List;

/**
 * @author WangLe
 * @date 2018/12/5 17:07
 * @description
 */
public interface JobService {

    /**
     * 查询所有的定时任务
     *
     * @return
     */
    List<JobEntity> findAll();

    /**
     * 保存定时任务
     *
     * @param jobEntity
     */
    void saveJob(JobEntity jobEntity);

    /**
     * 更新定时任务
     *
     * @param jobEntity
     */
    void updateJob(JobEntity jobEntity);

    /**
     * 批量删除定时任务
     *
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 批量更新定时任务状态
     *
     * @param ids
     */
    void updateStatusBatch(Long[] ids);

    /**
     * 立即执行
     *
     * @param ids
     */
    void run(Long[] ids);

    /**
     * 批量暂停
     *
     * @param ids
     */
    void pause(Long[] ids);

    /**
     * 批量恢复
     *
     * @param ids
     */
    void resume(Long[] ids);


}
