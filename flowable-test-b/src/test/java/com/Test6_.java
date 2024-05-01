package com;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * ClassName: Test6_
 * Package: com
 * Description:
 *
 * @Author R
 * @Create 2024/5/1 9:50
 * @Version 1.0
 */
public class Test6_ {
    //部署
    @Test
    public void test1() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("请假流程-排他网关.bpmn20.xml")
                .name("排他网关")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println(deploy.getName());
        //deploy.getId() = 107501
        //排他网关
        //holiday-exclusive:1:107504
    }
    //启动
    @Test
    public void test2() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("num",3);
        runtimeService.startProcessInstanceById("holiday-exclusive:1:107504",variables);
    }

    //执行
    //如果从网关出去的线所有条件都不满足的情况下会抛出系统异常，但是要注意任务没有介绍，还是原来的任务，我们可以重置流程变量

    /**
     * 前面我们可以直接在连接线上定义条件，那为什么还要有排他网关呢？直接在线上的情况，如果条件都不满足，流程就结束了，是异常结束!!
     */
    @Test
    public void test3() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("110001")
                .taskAssignee("zhangsan")
                .singleResult();
        if(task != null){
            // 完成任务
            taskService.complete(task.getId());
            System.out.println("完成Task");
        }
    }
}
