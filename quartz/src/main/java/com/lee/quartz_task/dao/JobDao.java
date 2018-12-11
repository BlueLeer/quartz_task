package com.lee.quartz_task.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.quartz_task.model.JobEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WangLe
 * @date 2018/12/5 15:33
 * @description
 */
@Mapper
public interface JobDao extends BaseMapper<JobEntity> {

}
