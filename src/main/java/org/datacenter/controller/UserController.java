package org.datacenter.controller;

import org.datacenter.model.User;
import org.datacenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired UserService userService;
	
	@RequestMapping("/users/{username}")
	public String getUserInfo(@PathVariable String username) {
		return null;
	}	
}
