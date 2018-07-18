package com.controller;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;

import com.entity.User;
import com.util.JbpmTestCase;
import com.util.JbpmUtil;


/**
 * 流程变量和任务变量的关系：流程变量类似成员变量，任务变量类似局部变量，流程变量在整个流程的执行中每个环节都能获取到，而任务变量只有在当前环节能获取到当前的环节绑定的任务变量
 * @author v.cuijq
 *
 */
public class TestDecision extends JbpmTestCase implements JbpmUtil{

	@Override
	public void deploy() {
		super.setUp();
		repositoryService.createDeployment().addResourceFromClasspath("jpdl/decision.jpdl.xml").deploy();
	}

	@Override
	public void createInstance() {
		super.setUp();
		Map<String,Object> map = new HashMap<>();
		map.put("node", "大于2天小于10天");//选择去哪个节点
		//创建流程 同时 新增流程变量
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("decision",map);
		print("流程实例ID",processInstance.getId());
	}

	@Override
	public void getCurrentActivity() {
		String activityName = executionService.createProcessInstanceQuery().processInstanceId("decision.260001").uniqueResult().findActiveActivityNames().toString();
		System.out.println("流程当前所在节点："+activityName);
	}

	@Override
	public void getTask() {
		List<Task> tasks = taskService.findPersonalTasks("B");
		for(int i = 0;i<tasks.size();i++) {
			Task task = tasks.get(i);
			System.out.println("任务名称："+task.getActivityName()+" 处理人："+task.getAssignee() +" 任务ID:"+task.getId());
		}
		
	}

	@Override
	public void completeTask() {
		// TODO Auto-generated method stub
		taskService.completeTask("260004");
	}
	//新增任务变量
	public void addTaskVar() {
		Map<String,Object> map = new HashMap<>();
		map.put("deptManager", "100");
		map.put("consigneeName", "GYN");
		//任务id和任务变量
		taskService.setVariables("80005", map);
	}
	//获取任务变量
	public void getTaskVar() {
		Map<String,Object> map = new HashMap<>();
		map.put("consigneeId", "100");
		map.put("consigneeName", "GYN");
		//任务id和任务参数
		String value = taskService.getVariable("80005", "consigneeId").toString();
		System.out.println("value："+value);
	}
	
	//流程变量获取
	public void getInstanceVar() {
		Set<String> set = executionService.getVariableNames("test.80001");
		Iterator it = set.iterator();
		while(it.hasNext()) {
			System.out.print(it.next()+", ");
		}
		
		String value = (String)executionService.getVariable("test.80001", "userId");
		System.out.println();
		System.out.println("value："+value);
	}
	//流程变量更新
	public void updateInstanceVar() {
		executionService.setVariable("el.120001", "deptManager","cnm");
	}
}
