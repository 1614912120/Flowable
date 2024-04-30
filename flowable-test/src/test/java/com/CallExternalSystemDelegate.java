package com;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * ClassName: CallExternalSystemDelegate
 * Package: com
 * Description:
 *
 * @Author R
 * @Create 2024/4/29 15:01
 * @Version 1.0
 */
public class CallExternalSystemDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("为员工"+execution.getVariable("employee")+"调用外部系统");
    }
}
