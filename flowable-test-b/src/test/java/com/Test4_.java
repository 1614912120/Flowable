package com;

/**
 * ClassName: Test4_
 * Package: com
 * Description:
 *
 * @Author R
 * @Create 2024/4/30 22:25
 * @Version 1.0
 */

import org.flowable.engine.IdentityService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 用户和组的维护
 */
public class Test4_ {
    /**
     * 维护用户
     */
    @Test
    public void test() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = defaultProcessEngine.getIdentityService();
//        User user = identityService.newUser("李飞");
//        user.setFirstName("li");
//        user.setLastName("fei");
//        user.setEmail("lifei@qq.com");
        User user = identityService.newUser("第三方");
        user.setFirstName("di");
        user.setLastName("sanfang");
        user.setEmail("disanfang@qq.com");
        identityService.saveUser(user);
    }
    /**
     * 创建用户组
     */
    @Test
    public void test2() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = defaultProcessEngine.getIdentityService();
        Group group1 = identityService.newGroup("group1");
        group1.setName("销售部");
        group1.setType("type1");
        Group group2 = identityService.newGroup("group2");
        group2.setName("开发部");
        group2.setType("type2");
        identityService.saveGroup(group2);
    }

    /**
     * 关联 分配用户到对应组种
     */
    @Test
    public void test3() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = defaultProcessEngine.getIdentityService();
        //根据组的编号找group对象
        Group group1 = identityService.createGroupQuery().groupId("group1").singleResult();
        List<User> list = identityService.createUserQuery().list();
        for (User user : list) {
            identityService.createMembership(user.getId(),group1.getId());
        }

    }
}
