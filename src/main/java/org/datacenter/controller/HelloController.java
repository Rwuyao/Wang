package org.datacenter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
public class HelloController {
	
	@RequestMapping("/Hello")
	public String welcome() {
		return JSON.toJSONString("你好");
	}
}
	
