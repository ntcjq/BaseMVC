package com.util;

import org.jbpm.api.Configuration;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;

import junit.framework.TestCase;

public class JbpmTestCase extends TestCase{
	protected RepositoryService repositoryService;//部署流程服务
	protected ExecutionService executionService;//流程执行服务
	protected TaskService taskService;//任务服务
	protected HistoryService historyService;//历史服务
	protected ManagementService managementService;//流程管理服务
	protected IdentityService identityService;//身份认证服务
	
	protected void setUp() {
		ProcessEngine processEngine = Configuration.getProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		executionService = processEngine.getExecutionService();
		taskService = processEngine.getTaskService();
		historyService = processEngine.getHistoryService();
		managementService = processEngine.getManagementService();
		identityService = processEngine.getIdentityService();
	} 
	
	public void print(String name,String value) {
		System.out.println(name +"================"+ value);
	}
}
