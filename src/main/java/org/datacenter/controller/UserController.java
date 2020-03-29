package org.datacenter.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.datacenter.model.User;
import org.datacenter.service.UserService;
import org.datacenter.utils.Result;
import org.datacenter.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired UserService userService;
	
	/**
	 * 获取用户信息
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/{username}",method= RequestMethod.GET)
	public Result checkUserName(@PathVariable String username) {
		if(StringUtils.isNotBlank(username)) {
			return Result.fail("username不能为空");
		}
		//检查该用户是否已经被注册
		if(userService.isExits(username)){
			return Result.fail("账号已经存在");
		}else {
			return Result.SUCCESS();
		}	
	}
	
	/**
	 * 注册用户
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/register",method= RequestMethod.POST)
	public Result register(@RequestBody User user) {
		//检查必要参数是否存在
		if(user==null) {
			return Result.of(ResultEnum.Parameter_Is_Empty);
		}
		String username=user.getUsername();
		String password=user.getPassword();		
		if(StringUtils.isNotBlank(username)) {
			return Result.fail("未获取到username！！");
		}
		if(StringUtils.isNotBlank(password)) {
			return Result.fail("未获取到password！！");
		}	
		
		//检查该用户是否已经被注册
		if(userService.isExits(username)){
			return Result.fail("账号["+username+"]已被注册！！");
		}		
		//插入记录
		try {
			//对密码加密
			PasswordEncoder encoder=new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(password));		
			//设置日期
			user.setCreatetime(new Date());
			userService.insert(user);
		}catch(Exception e) {
			return Result.fail("账号["+username+"]注册失败，请重新尝试！！");
		}
		return Result.SUCCESS();
	}
	
}
