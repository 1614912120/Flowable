package com;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * ClassName: Test8_
 * Package: com
 * Description:
 *
 * @Author R
 * @Create 2024/5/1 11:04
 * @Version 1.0
 */

/**
 * 包容网关
 */
public class Test8_ {
    @Test
    public void test1() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("")
                .name("并行网关")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println(deploy.getName());
        //deploy.getId() = 115001
        //并行网关
        //bingxing:1:115004
    }

    @Test
    public void test2() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("num",1);
        runtimeService.startProcessInstanceById("",variables);
    }

    @Test
    public void test3() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("")
                .taskAssignee("zhangsan")
                .singleResult();
        if(task != null) {
            taskService.complete(task.getId());
            System.out.println("完成task");
        }
    }
}
