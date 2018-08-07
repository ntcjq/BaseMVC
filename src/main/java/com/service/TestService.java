package com.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.multipleDataSource.CustomerContextHolder;

@Service(value="testService")
public class TestService {

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	
	
	@Transactional
	public void saveMultipleTx() {
		Map dept = new HashMap();
		dept.put("deptNo", 3);
		dept.put("deptName", "第一");
		sqlSessionTemplate.insert("testMapper.testTx",dept);//这是master库的表
		System.out.println("第一条成功");
		System.out.println("当前数据源"+CustomerContextHolder.getCustomerType());
		//切换数据源 参数为配置的key
		CustomerContextHolder.setCustomerType("ds_slave");
		System.out.println("当前数据源"+CustomerContextHolder.getCustomerType());
		Map user = new HashMap();
		user.put("nick_name", "昵称");
		sqlSessionTemplate.insert("testMapper.testTx2",user);//这是slave库 的表
		System.out.println("第二条成功");
//		System.out.println(1/0);
	}
	
}
