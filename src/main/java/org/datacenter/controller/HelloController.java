package org.datacenter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/Hello")
	public String welcome() {
		return "HelloGitV1";
	}
}
	
