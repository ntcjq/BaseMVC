package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	
	@RequestMapping(value="testJson")
	@ResponseBody
	public String testJson(@RequestBody Map map) {
		return map.toString();  
	}
	
	@RequestMapping(value="test")
	@ResponseBody
	public String test(String var) {
		return var;  
	}
}
