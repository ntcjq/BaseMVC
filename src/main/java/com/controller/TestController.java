package com.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.TestService;

@Controller
public class TestController {

	@Resource(name="testService")
	private TestService testService;
	
	@RequestMapping(value="testJson")
	@ResponseBody
	public String testJson(@RequestBody Map map) {
		return map.toString();  
	}
	
	@RequestMapping(value="test")
	@ResponseBody
	public String test() {
		testService.saveMultipleTx();
		return "test";  
	}
}
