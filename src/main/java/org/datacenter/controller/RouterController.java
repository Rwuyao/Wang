package org.datacenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {
	/*首页*/
	@RequestMapping("/")
    public String index() {
        return "index";
    }
	
	@RequestMapping("/index")
    public String chatroom() {
        return "index";
    }
	/*登录*/
	@RequestMapping("/signin")
    public String login() {
        return "login";
    }
	
	/*注册*/
	@RequestMapping("/join")
    public String join() {
        return "register";
    }
	
	
}
