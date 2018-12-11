package com.lee.quartz_task.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangLe
 * @date 2018/12/5 15:26
 * @description
 */
@TableName("job")
@Data
public class JobEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // 任务ID
    @TableId
    private Long jobId;
    // 任务bean名称
    private String beanName;
    // 执行的方法
    private String methodName;
    // 参数
    private String params;
    // cron表达式
    private String cronExpression;
    // 状态
    private Integer status;
    // 备注
    private String remark;
    //  创建时间
    private Date createDate;

    // 逻辑删除标识,在配置文件中配置,当前配置,当为1时表示已经被删除,当为0时没有被删除
    // 所谓的逻辑删除,就是数据没有真正的从磁盘中删除,而是执行一条更新语句,将该条记录标识为不可用状态
    @TableLogic
    private Integer deleted;
}

