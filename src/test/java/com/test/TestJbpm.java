package com.test;

import java.util.List;

import org.jbpm.api.Configuration;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

import junit.framework.TestCase;

public class TestJbpm extends TestCase{

	//部署流程
	public void depoly() {
		ProcessEngine processEngine = Configuration.getProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addResourceFromClasspath("jpdl/test.jpdl.xml").deploy();
	} 
	//创建流程实例   
	public void crateInstance() {
		ProcessEngine processEngine = Configuration.getProcessEngine();
		ExecutionService executionService = processEngine.getExecutionService();
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("test");
		System.out.println(processInstance.getId());
	} 
	//查询流程实例当前所在节点
	public void getCurrentActivity() {
		ProcessEngine processEngine = Configuration.getProcessEngine();
		ExecutionService executionService = processEngine.getExecutionService();
		//参数是crateInstance()的processInstance.getId()  也可以在t_engine_execution表中查找id
		String activityName = executionService.createProcessInstanceQuery().processInstanceId("test.10001").uniqueResult().findActiveActivityNames().toString();
		System.out.println("当前任务所在节点："+activityName);
	} 
	//获取对应人员的任务
	public void getTask() {
		ProcessEngine processEngine = Configuration.getProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.findPersonalTasks("李四");
		for(int i = 0;i<tasks.size();i++) {
			Task task = tasks.get(i);
			System.out.println("任务名称："+task.getActivityName()+" 处理人："+task.getAssignee() +" 任务ID:"+task.getId());
		}
	} 
	//完成任务
	public void completeTask() {
		ProcessEngine processEngine = Configuration.getProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		//getTask中获取的任务ID
		taskService.completeTask("30001");
	} 
}
