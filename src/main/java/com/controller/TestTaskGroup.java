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
public class TestTaskGroup extends JbpmTestCase implements JbpmUtil{

	@Override
	public void deploy() {
		super.setUp();
		
		String group = identityService.createGroup("manager");
		//关键是第一个参数
		identityService.createUser("g1", "aa1", "aaa1");
		identityService.createUser("g2", "aa2", "aaa2");
		identityService.createMembership("g1", group);
		identityService.createMembership("g2", group);
		repositoryService.createDeployment().addResourceFromClasspath("jpdl/taskGroup.jpdl.xml").deploy();
	}

	@Override
	public void createInstance() {
		super.setUp();
		User user = new User();
		user.setUserName("cjq");
		Map<String,Object> map = new HashMap<>();
		map.put("user", user);
		//创建流程 同时 新增流程变量
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("taskGroup",map);
		print("流程实例ID",processInstance.getId());
	}

	@Override
	public void getCurrentActivity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTask() {
		//注意这边是findGroupTasks，和前面的findPersonalTasks不一样
		List<Task> tasks = taskService.findGroupTasks("g1");
		for(int i = 0;i<tasks.size();i++) {
			Task task = tasks.get(i);
			System.out.println("任务名称："+task.getActivityName()+" 处理人："+task.getAssignee() +" 任务ID:"+task.getId());
		}
		
	}
	//给group中的成员分配任务
	public void takeTask(){
		//给group中的人员分配任务   参数：任务id，组员
		taskService.takeTask("210004", "g1");
	}

	@Override
	public void completeTask() {
		// TODO Auto-generated method stub
		taskService.completeTask("210004");
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
