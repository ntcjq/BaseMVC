package com.service;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

public class MyDecisionHandler implements DecisionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution exec) {
		String to =exec.getVariable("to").toString();//这边是流程创建时传的参数
		if("小于2天".equals(to)) {
			return "小于2天";//此处返回transition的name
		}else if("大于2天小于10天".equals(to)){
			return "大于2天小于10天";
		}else {
			return "10天以上";
		}
	}
}
