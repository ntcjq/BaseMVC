package com.service;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.multipleDataSource.CustomerContextHolder;

@Service(value="testService")
public class TestService {

	@Resource(name="masterJdbcTemplate")
	private JdbcTemplate masterJdbcTemplate;
	
	@Resource(name="slaveJdbcTemplate")
	private JdbcTemplate slaveJdbcTemplate;
	
	
	
	@Transactional
	public void saveMultipleTx() {
		masterJdbcTemplate.update("insert into dept values (3,'deptNo')");
		System.out.println("第一条成功");
		slaveJdbcTemplate.update("insert into user (nick_name) values ('deptNo')");
		System.out.println("第二条成功");
		System.out.println(1/0);
	}
	
}
