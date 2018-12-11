package com.lee.quartz_task.model;

import com.lee.quartz_task.common.util.RTException;
import com.lee.quartz_task.common.util.SpringContextUtils;
import org.springframework.remoting.RemoteTimeoutException;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author WangLe
 * @date 2018/12/11 14:38
 * @description
 */
public class JobRunnable implements Runnable {

    private Object target;
    private Method method;
    private String params;

    public JobRunnable(String beanName, String methodName, String params) throws NoSuchMethodException {
        this.target = SpringContextUtils.getBean(beanName);
        this.params = params;

        if (StringUtils.isEmpty(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        }
    }

    @Override
    public void run() {
        try {
            // 设置方法可以访问(例如对于私有方法,这里需要设置为可以访问)
            ReflectionUtils.makeAccessible(method);
            if (!StringUtils.isEmpty(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RTException("非法访问方法!");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RTException("非法执行的对象!");
        }
    }
}
