package com.controller;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;

import com.util.JbpmTestCase;
import com.util.JbpmUtil;


/**
 * 流程变量和任务变量的关系：流程变量类似成员变量，任务变量类似局部变量，流程变量在整个流程的执行中每个环节都能获取到，而任务变量只有在当前环节能获取到当前的环节绑定的任务变量
 * @author v.cuijq
 *
 */
public class TestVar extends JbpmTestCase implements JbpmUtil{

	@Override
	public void deploy() {
		// TODO Auto-generated method stub
		super.setUp();
		repositoryService.createDeployment().addResourceFromClasspath("jpdl/test.jpdl.xml").deploy();
	}

	@Override
	public void createInstance() {
		super.setUp();
		Map<String,Object> map = new HashMap<>();
		map.put("userId", "001");
		map.put("userName", "cjq");
		map.put("title", "请假");
		//创建流程 同时 新增流程变量
//		ProcessInstance processInstance = executionService.startProcessInstanceByKey("test",map);
		ProcessInstance processInstance = executionService.startProcessInstanceById("test-1", map);
		
		print("流程实例ID",processInstance.getId());
	}

	@Override
	public void getCurrentActivity() {
		// TODO Auto-generated method stub
		String activityName = executionService.createProcessInstanceQuery().processInstanceId("test.70001").uniqueResult().findActiveActivityNames().toString();
		System.out.println("流程当前所在节点："+activityName);
	}

	@Override
	public void getTask() {
		System.out.println(executionService.findProcessInstanceById("test.100001"));
		
		//根据流程实例id查询当前需要执行的task的id
		Task currentTask = taskService.createTaskQuery().processInstanceId("test.100001").uniqueResult();
		String taskId= currentTask.getId();
		String taskName = currentTask.getName();
		System.out.println(taskId+":"+taskName);
		
//		List<Task> list = taskService.createTaskQuery().processInstanceId("test.70001").list();
//		for(Task task :list) {
//			System.out.println(task.getId() +":"+ task.getName());
//		}
	}

	@Override
	public void completeTask() {
		// TODO Auto-generated method stub
		taskService.completeTask("100005");
	}
	//新增任务变量
	public void addTaskVar() {
		Map<String,Object> map = new HashMap<>();
		map.put("consigneeId", "100");
		map.put("consigneeName", "GYN");
		//任务id和任务变量
		taskService.setVariables("40001", map);
	}
	//获取任务变量
	public void getTaskVar() {
		Map<String,Object> map = new HashMap<>();
		map.put("consigneeId", "100");
		map.put("consigneeName", "GYN");
		//任务id和任务参数
		String value = taskService.getVariable("40001", "consigneeId").toString();
		System.out.println("value："+value);
	}
	
	//流程变量获取
	public void getInstanceVar() {
		Set<String> set = executionService.getVariableNames("test.10001");
		Iterator it = set.iterator();
		while(it.hasNext()) {
			System.out.print(it.next()+", ");
		}
		
		String value = (String)executionService.getVariable("test.10001", "userId");
		System.out.println();
		System.out.println("value："+value);
	}
	//流程变量更新
	public void updateInstanceVar() {
		executionService.setVariable("test.80001", "userId","002");
	}
	
	public void test() {
		executionService.signalExecutionById("test.120001");
	}
}
