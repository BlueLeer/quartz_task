package com.lee.quartz_task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.quartz_task.common.util.ScheduleUtils;
import com.lee.quartz_task.dao.JobDao;
import com.lee.quartz_task.model.JobEntity;
import com.lee.quartz_task.service.JobService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangLe
 * @date 2018/12/5 17:07
 * @description
 */
@Service("jobService")
public class JobServiceImpl extends ServiceImpl<JobDao, JobEntity> implements JobService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void saveJob(JobEntity jobEntity) {
        this.save(jobEntity);
        ScheduleUtils.createJob(scheduler, jobEntity);
    }

    @Override
    public void updateJob(JobEntity jobEntity) {

    }

    @Override
    public void deleteBatch(Long[] ids) {

    }

    @Override
    public void updateStatusBatch(Long[] ids) {

    }

    @Override
    public void run(Long[] ids) {

    }

    @Override
    public void pause(Long[] ids) {

    }

    @Override
    public void resume(Long[] ids) {

    }

    @Override
    public List<JobEntity> findAll() {
        List<JobEntity> list = this.list();
        return list;
    }
}
