package com.controller;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.Configuration;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

import com.util.JbpmTestCase;
import com.util.JbpmUtil;


/**
 * 流程变量和任务变量的关系：流程变量类似成员变量，任务变量类似局部变量，流程变量在整个流程的执行中每个环节都能获取到，而任务变量只有在当前环节能获取到当前的环节绑定的任务变量
 * @author v.cuijq
 *
 */
public class TestState extends JbpmTestCase implements JbpmUtil{

	@Override
	public void deploy() {
		super.setUp();
		repositoryService.createDeployment().addResourceFromClasspath("jpdl/state.jpdl.xml").deploy();
	}

	@Override
	public void createInstance() {
		super.setUp();
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("state");
		print("流程实例ID",processInstance.getId());
	}

	@Override
	public void getCurrentActivity() {
		String activityName = executionService.createProcessInstanceQuery().processInstanceId("state.20001").uniqueResult().findActiveActivityNames().toString();
		System.out.println("流程当前所在节点："+activityName);
	}

	@Override
	public void getTask() {
		
	}

	@Override
	public void completeTask() {
		executionService.signalExecutionById("state.20001");
	}
}
