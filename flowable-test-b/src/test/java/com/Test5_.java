package com;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.idm.api.Group;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: Test5_
 * Package: com
 * Description:
 *
 * @Author R
 * @Create 2024/4/30 23:07
 * @Version 1.0
 */
public class Test5_ {
    @Test
    public void test1(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("请假流程-候选人组.bpmn20.xml")
                .name("请假流程-候选人组")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println(deploy.getName());
        //deploy.getId() = 97501
        //请假流程-候选人组
        //holiday-group:1:97504
    }

    /**
     * 启动流程
     */

    @Test
    public void test2() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = defaultProcessEngine.getIdentityService();
        Group group1 = identityService.createGroupQuery().groupId("group1").singleResult();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        // 给流程定义中的UEL表达式赋值
        Map<String,Object> variables = new HashMap<>();
        // variables.put("g1","group1");
        variables.put("g1",group1.getId()); // 给流程定义中的UEL表达式赋值
        runtimeService.startProcessInstanceById("holiday-group:1:97504",variables);
    }

    /**
     * 根据登录用户查询对应拾取任务
     */
    @Test
    public void queryTaskCandidateGroup(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = defaultProcessEngine.getIdentityService();
        Group group = identityService.createGroupQuery().groupMember("李飞").singleResult();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery()
                .processInstanceId("100001")
                .taskCandidateGroup(group.getId())
                .list();
        for (Task task : list) {
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getName() = " + task.getName());
        }
    }

    /**eer
     * 拾取任务
     *    一个候选人拾取了这个任务之后其他的用户就没有办法拾取这个任务了
     *    所以如果一个用户拾取了任务之后又不想处理了，那么可以退还
     */
    @Test
    public void claimTaskCandidate(){
        String id = "李飞";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 根据当前登录的用户找到对应的组
        IdentityService identityService = processEngine.getIdentityService();
        Group group = identityService.createGroupQuery().groupMember(id).singleResult();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("100001")
                .taskCandidateGroup(group.getId())
                .singleResult();
        if(task != null) {
            // 任务拾取
            taskService.claim(task.getId(),id);
            System.out.println("任务拾取成功");
        }
    }

    @Test
    public void completeTask() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("100001")
                .taskAssignee("李飞")
                .singleResult();
        if(task != null) {
            taskService.complete(task.getId());
            System.out.println("完成task");
        }

    }

}
