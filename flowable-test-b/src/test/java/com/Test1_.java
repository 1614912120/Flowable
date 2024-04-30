package com;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.junit.jupiter.api.Test;

/**
 * ClassName: Test1_
 * Package: com
 * Description:
 *
 * @Author R
 * @Create 2024/4/30 14:49
 * @Version 1.0
 */
public class Test1_ {
    /**
     * 加载默认的配置文件
     */
    @Test
    public void processEngine02() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("defaultProcessEngine"+ defaultProcessEngine);
    }

    /**
     * 加载自定义配置文件
     */
    @Test
    public void processEngine03() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("flowable.cfg.xml");
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);

    }
}
