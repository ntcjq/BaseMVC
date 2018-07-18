package com.util;

public interface JbpmUtil {

	public void deploy();//部署
	
	public void createInstance();//创建流程实例
	
	public void getCurrentActivity();//查询流程实例当前所在节点
	
	public void getTask();//获取对应人员的任务
	
	public void completeTask();//完成任务
	
}
