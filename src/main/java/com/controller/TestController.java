package com.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestAction {

	
	@RequestMapping(value="testJson",method=RequestMethod.GET)
	@ResponseBody
	public String testJson() {
		Map map = new HashMap();
		map.put("name", "cjq");
		return map.toString();  
	}
}
