package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("jbpm")
@Controller
public class TestController {

//	@Resource(name="processEngine")
//	ProcessEngine processEngine;
	
	@Resource(name="repositoryService")
	RepositoryService repositoryService;
	
	@Resource(name="executionService")
	ExecutionService executionService;
	
	@Resource(name="taskService")
	TaskService taskService;
	
	
	
	
	@RequestMapping("test")
	@ResponseBody
	public String test(@RequestParam("cn") String  cn) {
		return cn; 
	}
	
	@RequestMapping("deploy")
	@ResponseBody
	public void deploy(String filename) {
		repositoryService.createDeployment().addResourceFromClasspath("jpdl/"+filename+".jpdl.xml").deploy();
	}
	
	@RequestMapping("createInstance")
	@ResponseBody
	public Object createInstance(String key) {
		Map map = new HashMap();
		map.put("memberVar", "memberValue");
		ProcessInstance processInstance =  executionService.startProcessInstanceByKey(key, map);
		Task currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).uniqueResult();
		Map result = new HashMap();
		result.put("processInstanceId", processInstance.getId());
		result.put("taskId", currentTask.getId());
		return result;
	}
	
	@RequestMapping("completeTask")
	@ResponseBody
	public Object completeTask(String processInstanceId,String taskId) {
		taskService.completeTask(taskId);
		Task currentTask = taskService.createTaskQuery().processInstanceId(processInstanceId).uniqueResult();
		
		ProcessInstance processInstance = executionService.findProcessInstanceById(processInstanceId);
		Map result = new HashMap();
		result.put("processInstanceId", processInstanceId);
		if(processInstance != null) {
			result.put("taskId", currentTask.getId());
		}else {
			result.put("isEnded", "end");
		}
		return result;
	}
	
	
	/**
	 * 实现自由流无非就是新建一个transition让它指向我们的目标节点，流转到目标节点以后我们必要的需要将这个新的transition给删除，以免影响其他的业务流
	 * @param taskId  当前的任务id
	 * @param endDom  新加的自由流  流向的节点名称
	 */
	@RequestMapping("createFreedom")
	@ResponseBody
	 public void createFreedom(String taskId, String endDom) {
	        Task task = taskService.getTask(taskId);
	        String activityName = task.getActivityName();
	        Execution execution = executionService.findExecutionById(task.getExecutionId());
	        ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) repositoryService
	                .createProcessDefinitionQuery().processDefinitionId(execution.getProcessDefinitionId()).uniqueResult();

	        // 寻找一个活跃在给定activityName执行
	        //execution.findActiveExecutionIn(activityName);
	        // 递归搜索给定的活动元素,包括这个活动和所有子活动(当前活动节点名称)
	        ActivityImpl activityImpl = processDefinitionImpl.findActivity(activityName);
	        // 目标节点名称
	        ActivityImpl goleActivityImpl = processDefinitionImpl.findActivity(endDom);

	        TransitionImpl transitionImpl = activityImpl.createOutgoingTransition();
	        transitionImpl.setName("新的teansition");
	        transitionImpl.setDestination(goleActivityImpl);
	        activityImpl.addOutgoingTransition(transitionImpl);

	        // 完成任务
	        taskService.completeTask(task.getId(), transitionImpl.getName());
	        // 删除创建的流转路线，这个一般都会删除一下，防止影响原本流程定义
	        this.removeOutTransition(processDefinitionImpl, activityName, endDom);

	    }

		/**
		 * 删除自由流
		 * @param pd
		 * @param sourceName
		 * @param destName
		 */
	    public void removeOutTransition(ProcessDefinitionImpl pd, String sourceName, String destName) {

	        ActivityImpl sourceActivity = pd.findActivity(sourceName);
	        @SuppressWarnings("unchecked")
	        List<Transition> trans = (List<Transition>) sourceActivity.getOutgoingTransitions();
	        for (Transition tran : trans) {
	            if (destName.equals(tran.getDestination().getName())) {
	                trans.remove(tran);
	                break;
	            }
	        }
	    }
	
}
