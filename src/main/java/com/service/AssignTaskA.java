package com.service;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

public class AssignTaskA implements AssignmentHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void assign(Assignable arg0, OpenExecution arg1) throws Exception {
		
		arg0.setAssignee("userA");
	}

}
