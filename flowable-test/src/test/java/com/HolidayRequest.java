package com;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.flowable.common.engine.impl.AbstractEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;

/**
 * ClassName: HolidayRequest
 * Package: com
 * Description:
 *
 * @Author R
 * @Create 2024/4/29 14:24
 * @Version 1.0
 */
public class HolidayRequest {
    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = cfg.buildProcessEngine();




        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .singleResult();
        System.out.println("找到的流程定义"+processDefinition);

        Scanner scanner = new Scanner(System.in);
        System.out.println("你是谁");
        String employee = scanner.nextLine();
        System.out.println("你需要几天假期");
        Integer nrOfHoliday = Integer.valueOf(scanner.nextLine());
        System.out.println("申请的理由是？");
        String des = scanner.nextLine();


        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee",employee);
        variables.put("nrOfHoliday",nrOfHoliday);
        variables.put("des",des);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);


        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("“You have"+tasks.size()+"tasks:");
        for (int i =0;i<tasks.size();i++) {
            System.out.println((i+1)+")"+tasks.get(i).getName());
        }
        System.out.println("你想完成那件任务");
        Integer taskIndex = Integer.valueOf(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariable = taskService.getVariables(task.getId());
        System.out.println(processVariable.get("employee")+"想要"+processVariable.get("nrOfHoliday")+"天假期，你同意吗");


        boolean approved = scanner.nextLine().toLowerCase().equals("是");
        variables = new HashMap<>();
        variables.put("approved",approved);
        taskService.complete(task.getId(),variables);


        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                .processDefinitionId(processInstance.getId())
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId()+" took "+activity.getDurationInMillis()+" milliseconds ");
        }
    }
}
