package com.service;

import javax.annotation.Resource;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="jbpmService")
public class JbpmService {

	
	@Resource(name="repositoryService")
	RepositoryService repositoryService;
	
	@Resource(name="executionService")
	ExecutionService executionService;
	
	@Resource(name="taskService")
	TaskService taskService;
	
	@Transactional
	public void completeTask(String taskId) {
		taskService.completeTask(taskId);
		System.out.println(1/0);
	}
}
