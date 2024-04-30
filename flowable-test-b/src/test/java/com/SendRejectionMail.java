package com;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * ClassName: SendRejectionMail
 * Package: com
 * Description:
 *
 * @Author R
 * @Create 2024/4/30 11:15
 * @Version 1.0
 */
public class SendRejectionMail implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("请假被拒绝,,,安心工作吧");
    }
}
