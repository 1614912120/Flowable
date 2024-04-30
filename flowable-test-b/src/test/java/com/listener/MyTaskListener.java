package com.listener;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * ClassName: MyTaskListener
 * Package: com.listener
 * Description:
 *
 * @Author R
 * @Create 2024/4/30 20:27
 * @Version 1.0
 */
public class MyTaskListener implements TaskListener {
    /**
     * 监听器触发方法
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("监听器mytask触发了===="+delegateTask.getName()+"Assignee===="+delegateTask.getAssignee());
        if("提交请假流程".equals(delegateTask.getName())
        && "create".equals(delegateTask.getEventName())){
            delegateTask.setAssignee("小明");
        }else {
            delegateTask.setAssignee("小李");
        }
    }
}
