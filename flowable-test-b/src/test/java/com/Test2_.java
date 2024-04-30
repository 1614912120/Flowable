package com;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: Test2_
 * Package: com
 * Description:
 *
 * @Author R
 * @Create 2024/4/30 21:09
 * @Version 1.0
 */

//变量
public class Test2_ {
    @Test
    public void deploy() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("出差申请单.bpmn20.xml")
                .name("出差申请单")
                .deploy();

        System.out.println("deployId==="+deployment.getId());
        System.out.println(deployment.getName());
        //deployId===62501
        //出差申请单
        //evection:1:62504
    }


    @Test
    public void runProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 设置流程变量
        Map<String,Object> variables = new HashMap<>();
        // 设置assignee的取值
        variables.put("assignee0","张三");
        variables.put("assignee1","李四");
        variables.put("assignee2","王五");
        variables.put("assignee3","赵财务");
        runtimeService.startProcessInstanceById("evection:1:62504",variables);
    }

    /**
     * 完成任务 同时指定流程变量
     */
    @Test
    public void completeTask() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("65001")
                .taskAssignee("张三")
                .singleResult();
        HashMap<String, Object> processVariables = new HashMap<>();
        processVariables.put("num",2);
        taskService.complete(task.getId(), processVariables);
    }

    /**
     * 根据任务task编号来更新流程变量 局部变量
     */
    @Test
    public void updateVariableLocal() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("65001")
                .taskAssignee("李四")
                .singleResult();
        //在局部变量和全局变量都有的情况下，取出来的都是局部变量
        Map<String, Object> processVariables = task.getProcessVariables();
        processVariables.put("num",6);
        //设置局部变量
        taskService.setVariablesLocal(task.getId(),processVariables);
    }

    /**
     * 全局变量
     */
    @Test
    public void updateVariable() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("65001")
                .taskAssignee("李四")
                .singleResult();
        //在局部变量和全局变量都有的情况下，取出来的都是局部变量
        Map<String, Object> processVariables = task.getProcessVariables();
        processVariables.put("num",1);
        //设置全局变量
        taskService.setVariables(task.getId(),processVariables);
    }


    @Test
    public void completeTask2() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("65001")
                .taskAssignee("李四")
                .singleResult();
        taskService.complete(task.getId());
    }
}
