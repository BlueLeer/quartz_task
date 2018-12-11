package com.lee.quartz_task.controller;

import com.lee.quartz_task.common.util.Result;
import com.lee.quartz_task.model.JobEntity;
import com.lee.quartz_task.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author WangLe
 * @date 2018/12/5 17:09
 * @description
 */
@Controller
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

    /**
     * 保存任务
     *
     * @param jobEntity
     * @return
     */
    @PostMapping("/createJob")
    @ResponseBody
    public Result save(JobEntity jobEntity) {
        // 校验
        jobService.saveJob(jobEntity);
        return Result.ok();
    }


    @GetMapping("/listJob")
    public Result list() {
        List<JobEntity> all = jobService.findAll();
        Result result = Result.ok();
        result.put("list", all);
        return result;
    }

}
