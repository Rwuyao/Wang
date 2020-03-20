package org.datacenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

	@RequestMapping("/login")
    public String login() {
        return "login";
    }
	
	@RequestMapping("/")
    public String index() {
        return "chatroom";
    }
	
	@RequestMapping("/chatroom")
    public String chatroom() {
        return "chatroom";
    }
}
