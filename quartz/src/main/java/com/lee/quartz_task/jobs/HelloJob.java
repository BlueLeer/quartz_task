package com.lee.quartz_task.jobs;

import org.springframework.stereotype.Component;

/**
 * @author WangLe
 * @date 2018/12/11 15:53
 * @description
 */
@Component("helloJob")
public class HelloJob {
    public void hello(String msg) {
        System.out.println("----------" + msg);
    }
}
