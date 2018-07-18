package com.test;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.Execution;
import org.jbpm.api.ProcessInstance;
import org.jbpm.test.JbpmTestCase;

public class HelloJbpm extends JbpmTestCase {
    String deploymentId;

    protected void setUp() throws Exception {
        super.setUp();

        deploymentId = repositoryService.createDeployment()
                .addResourceFromClasspath("jpdl/Hello.jpdl.xml")
                .deploy();
        System.out.println("deploymentId:"+deploymentId);
    }

    protected void tearDown() throws Exception {
    	/* 
    	 * 删除一个流程定义会把它从数据库中删除。 
    	 * 如果在发布中的流程定义还存在活动的流程实例， 这个方法就会抛出异常。  
    	 * 如果希望级联删除一个发布中流程定义的 所有流程实例， 可以使用deleteDeploymentCascade 
    	 */  
        repositoryService.deleteDeploymentCascade(deploymentId);
    }

    public void testHello() {
        ProcessInstance processInstance = executionService
                .startProcessInstanceByKey("Hello");

        System.out.println("流程实例Id:" + processInstance.getId());
        System.out.println("流程定义Id:" + processInstance.getProcessDefinitionId());

        // 判断当前是否位于start节点
        System.out.println("是否位于start节点:" + processInstance.isActive("start"));

        // 判断当前是否位于state节点
        System.out.println("是否位于state1节点:" + processInstance.isActive("state1"));

        System.out.println("------------------------>使流程继续向下执行");
        //设置参数	
        Map map = new HashMap();
        map.put("key", "success");
        Execution executionInA = processInstance.findActiveExecutionIn("state1");
        assertNotNull(executionInA);

        // 判断当前是否位于state节点
        processInstance = executionService.signalExecutionById(executionInA.getId(),map);
        Execution executionInB = processInstance.findActiveExecutionIn("state2");
        assertNotNull(executionInB);
        
        System.out.println("是否位于state2节点:" + executionInB.isActive("state2"));
        //获取参数	
        String param = (String)executionService.getVariable(executionInB.getId(), "key");
        System.out.println(param);
        processInstance = executionService.signalExecutionById(executionInB.getId());
        Execution executionInC = processInstance.findActiveExecutionIn("state3");
        assertNotNull(executionInC);
        
        System.out.println("是否位于state3节点:" + executionInC.isActive("state3"));
        
    }

}