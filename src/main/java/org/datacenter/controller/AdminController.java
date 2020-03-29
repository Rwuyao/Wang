package org.datacenter.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired UserService userService;
	

	@RequestMapping(value="/getusers",method= RequestMethod.GET)
	public Result getusers(int limit,int page,String sort,String sortOrder,String begintime,String endtime,String username) {
		//检查必要参数是否存在
		List<User> rows;
		try {
			rows = userService.getuser(page, limit,sort, sortOrder, begintime, endtime, username);
			if(rows==null || rows.size()<=0) {	
				return Result.fail(0);
			}
		} catch (ParseException e) {
			return Result.fail(0);
		}	
		return Result.SUCCESS(rows,rows.size());
	}

	@RequestMapping(value="/edituser",method= RequestMethod.PUT)
	private Result edit(@RequestBody User user) {
		String username=user.getUsername();
		String password=user.getPassword();
		//检查用户名和密码是否为空
		 if(StringUtils.isNotBlank(username)) {
			 Result.fail("账号为空注册失败，请重新尝试！！");
		 }
		 if(StringUtils.isNotBlank(password)) {
			 Result.fail("密码为空注册失败，请重新尝试！！"); 
		 }
		 
		//更新
		if(userService.isExits(username)){
			try {
				userService.update(user);
				}catch(Exception e) {
					return Result.fail("账号["+user.getUsername()+"]更新失败，请重新尝试！！");
				}
		}else {
			//注册
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
		}	
	    return Result.SUCCESS();
	}
	
	@RequestMapping(value="/deleteuser/{username}",method= RequestMethod.DELETE)
	private Result del(@PathVariable String username) {
		try {
			userService.delete(username);
			}catch(Exception e) {
				return Result.fail("账号["+username+"]删除失败，请重新尝试！！");
			}
		    return Result.SUCCESS();
	}
	
}
