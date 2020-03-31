package org.datacenter.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.datacenter.model.Role;
import org.datacenter.model.User;
import org.datacenter.model.UserRole;
import org.datacenter.model.Userprofile;
import org.datacenter.service.RoleService;
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
	
	@Autowired RoleService roleService ;
	
	@RequestMapping(value="/deleteUserRole",method= RequestMethod.DELETE)
	private Result deletUserRole(UserRole userRole) {
		try {
			userService.deleteLinkUserAndRole(userRole);
			}catch(Exception e) {
				return Result.fail("解除角色绑定["+userRole.getRole()+"]失败，请重新尝试！！");
			}
		    return Result.SUCCESS();
	}
	
	
	@RequestMapping(value="/addUserRole",method= RequestMethod.PUT)
	public Result addrole(@RequestBody UserRole userRole) {
		//检查必要参数是否存在		
		if(userRole==null) {
			return Result.of(ResultEnum.Parameter_Is_Empty);
		}
		String   rolename=userRole.getRole();
		String   username=userRole.getUsername();
		if(!StringUtils.isNotBlank(rolename)) {
			return Result.fail("未获取到rolename！！");
		}		
		if(!StringUtils.isNotBlank(username)) {
			return Result.fail("未获取到username！！");
		}
		//检查角色是否存在
		if(!roleService.isExits(rolename)) {
			return Result.fail("角色["+rolename+"]不存在！！");
		}
		//检查用户和角色是否是绑定关系
		if(userService.hasLinkUserAndRole(username,rolename)) {
			return Result.SUCCESS();
		}
	
		//插入记录
		try {		
			 
			userService.saveLinkUserAndRole(new UserRole(username,rolename));
		}catch(Exception e) {
			return Result.fail("绑定["+rolename+"]失败，请重新尝试！！");
		}
		return Result.SUCCESS();
	}
	
	
	
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
	
	/**
	 * 获取用户详细信息
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/userprofile/{username}",method= RequestMethod.GET)
	public Result findUserProfile(@PathVariable String username) {
		if(!StringUtils.isNotBlank(username)) {
			return Result.fail("未获取到参数");
		}
		Userprofile userprofile =userService.getUserprofile(username);
		return Result.SUCCESS(userprofile);		
	}
	
	@RequestMapping(value="/edituserprofile",method= RequestMethod.PUT)
	private Result edituserprofile(@RequestBody User user) {
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
