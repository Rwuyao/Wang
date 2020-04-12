package org.datacenter.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.datacenter.model.Resource;
import org.datacenter.model.Role;
import org.datacenter.model.User;
import org.datacenter.model.UserRole;
import org.datacenter.model.Userprofile;
import org.datacenter.service.ResourceService;
import org.datacenter.service.RoleService;
import org.datacenter.service.UserService;
import org.datacenter.utils.Result;
import org.datacenter.utils.ResultEnum;
import org.datacenter.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	
	private static final PasswordEncoder encoder=new BCryptPasswordEncoder();
	
	@Value("${upload-path}")
	private String uploadPath;
	
	@Autowired UserService userService;
	
	@Autowired RoleService roleService ;
	
	@Autowired ResourceService resourceService;
	
	/**
	 *  角色配置
	 * */
	@RequestMapping(value="/getroles",method= RequestMethod.GET)
	public Result getroles(int limit,int page,String sort,String sortOrder,String rolename) {
		//检查必要参数是否存在
		PageInfo<Role> pageinfo;
		pageinfo = roleService.getrole(page, limit, sort, sortOrder,rolename);
		if(pageinfo==null || pageinfo.getTotal()<=0) {	
			return Result.fail();
		}		
		return Result.SUCCESS(pageinfo.getList(),(int)pageinfo.getTotal());
	}
	
	@RequestMapping(value="/editrole",method= RequestMethod.PUT)
	private Result editrole(@RequestBody Role role) {		
		if(role==null) {
			return Result.of(ResultEnum.Parameter_Is_Empty);
		}
		String rolename=role.getRolename();
		//检查用户名和密码是否为空
		 if(StringUtils.isNotBlank(rolename)) {
			 Result.fail("角色名为空更新失败，请重新尝试！！");
		 }		 				 	
		if(roleService.isExits(rolename)){
			//更新	
			try {
					roleService.updateRole(role);
			}catch(Exception e) {
					return Result.fail("角色["+rolename+"]更新失败，请重新尝试！！");
			}
		}else {
			//注册
			try {
				role.setCreatetime(new Date());
				roleService.saveRole(role);		
			}catch(Exception e) {
				return Result.fail("角色["+rolename+"]创建失败，请重新尝试！！");
			}
		}	
	    return Result.SUCCESS();
	}
	
	@RequestMapping(value="/deleterole/{rolename}",method= RequestMethod.DELETE)
	private Result deleterole(@PathVariable String rolename) {
		try {
			roleService.deleteRole(rolename); 
			}catch(Exception e) {
				return Result.fail("角色["+rolename+"]删除失败，请重新尝试！！");
			}
		    return Result.SUCCESS();
	}
	
	
	/**
	 *  菜单配置
	 * */
	@RequestMapping(value="/getMenuTree",method= RequestMethod.GET)
	public Result getMenu() {		
		List<Resource> nodes=resourceService.getAllResource(); 			
		return Result.SUCCESS(TreeUtil.data(nodes));		
	}
	
	
	@RequestMapping(value="/getMenuTable",method= RequestMethod.GET)
	public Result getMenuTable(Integer  parentid,int limit,int page,String sort,String sortOrder) {		
		List<Resource> nodes;
		if(parentid!=null) {
			nodes=resourceService.getAllResource(); 
			nodes=TreeUtil.data(nodes,parentid);
			return Result.SUCCESS(nodes,nodes.size());
		}else {
			PageInfo<Resource> pageinfo;
			pageinfo=resourceService.getAllResource(page, limit,sort,sortOrder); 
			return Result.SUCCESS(pageinfo.getList(),(int)pageinfo.getTotal());
		}				
				
	}
	
	@RequestMapping(value="/EditMenu",method= RequestMethod.PUT )
	public Result EditMenu(@RequestBody Resource resource) {
		//检查必要参数是否存在		
		if(resource==null) {
			return Result.of(ResultEnum.Parameter_Is_Empty);
		}
		Integer  Parentid=resource.getParentid();
		if(Parentid==null) {
			return Result.fail("未获取到必要参数！！");
		}		
		//检查该父目录是否存在
		if(!Parentid.equals(0)) {
			if(!resourceService.isExitsResource(Parentid)) {
				return Result.fail("父目录不存在！！");
			}
		}
		
		try {
			//检查资源是否存在，存在则更新，不存在则创建
			if(resourceService.isExitsResource(resource.getId())) {
				resourceService.updateResource(resource);
			}else {
				resource.setCreatetime(new Date());
				resource.setEnable(true);
				resourceService.saveResource(resource);
			}				
		}catch(Exception e) {
			return Result.fail("创建菜单["+resource.getResource()+"]失败，请重新尝试！！");
		}
		return Result.SUCCESS();
	}

	@RequestMapping(value="/deleteMenu/{id}",method= RequestMethod.DELETE)
	private Result deleteMenu(@PathVariable Integer id) {
		try {
			resourceService.deleteResource(id);			
			}catch(Exception e) {
				return Result.fail("删除失败，请重新尝试！！");
			}
		    return Result.SUCCESS();
	}
	
	/**
	 * 用户和角色建立关系
	 */
	@RequestMapping(value="/getUserRole",method= RequestMethod.GET)
	public Result getUserRole(int limit,int page,String sort,String sortOrder,String username) {
		if(!StringUtils.isNotBlank(username)) {
			return Result.fail("未获取到参数");
		}
		PageInfo<Role> pageinfo;
		pageinfo = userService.getuserRole(page, limit, sort, sortOrder, username);		
		return Result.SUCCESS(pageinfo.getList(),(int)pageinfo.getTotal());		
	}
	
	
	@RequestMapping(value="/deleteUserRole",method= RequestMethod.DELETE)
	private Result deletUserRole(@RequestBody UserRole userRole) {
		//检查必要参数是否存在		
		if(userRole==null) {
			return Result.of(ResultEnum.Parameter_Is_Empty);
		}
		try {
				userService.deleteUserRole(userRole);
		}catch(Exception e) {
				return Result.fail("解除角色绑定["+userRole.getRolename()+"]失败，请重新尝试！！");
		}
		return Result.SUCCESS();
	}
	
	
	@RequestMapping(value="/addUserRole",method= RequestMethod.PUT )
	public Result addrole(@RequestBody UserRole userRole) {
		//检查必要参数是否存在		
		if(userRole==null) {
			return Result.of(ResultEnum.Parameter_Is_Empty);
		}
		String   rolename=userRole.getRolename();
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
		if(userService.isExitsUserRole(username, rolename)) {
			return Result.SUCCESS();
		}
	
		//插入记录
		try {					 
			userService.saveUserRole(new UserRole(username,rolename));
		}catch(Exception e) {
			return Result.fail("绑定["+rolename+"]失败，请重新尝试！！");
		}
		return Result.SUCCESS();
	}
	
	

	/**
	 * 用户详细信息配置
	 */
	@RequestMapping(value="/userprofile/{username}",method= RequestMethod.GET)
	public Result findUserProfile(@PathVariable String username) {
		if(!StringUtils.isNotBlank(username)) {
			return Result.fail("未获取到参数");
		}
		Userprofile userprofile =userService.getUserprofile(username);
		return Result.SUCCESS(userprofile);		
	}
	
	
	
	@RequestMapping(value="/edituserprofile",method= RequestMethod.POST)
	private Result edituserprofile(MultipartFile files,User user,Userprofile userProfile,HttpServletRequest httpServletRequest) {
		//检查必要参数是否存在		
		if(user==null||userProfile==null) {
			return Result.of(ResultEnum.Parameter_Is_Empty);
		}	
		if(files!=null) {
			// 创建文件在服务器端的存放路径
			String dir =uploadPath;	            
            File fileDir = new File(dir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            // 生成文件在服务器端存放的名字
            String fileSuffix = files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + fileSuffix;
            File file = new File(fileDir + "/" + fileName);
            // 上传
            try {
            	files.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				return Result.fail("修改头像失败！！");
			}
            //更新头像地址
            userProfile.setHeadsculpture("/" + fileName);
		}                 		
		String username=user.getUsername();
		String password=user.getPassword();
		//检查用户名和密码是否为空
		 if(!StringUtils.isNotBlank(username)) {
			 Result.fail("账号为空修改失败，请重新尝试！！");
		 }
		 if(!StringUtils.isNotBlank(password)) {
			 Result.fail("密码为空修改失败，请重新尝试！！"); 
		 }
		 //检查密码是否为**********,否则将密码加密
		 if(!password.equals("**********")) {
			//对密码加密			
			user.setPassword(encoder.encode(password));	
		 }		 
		//更新用户信息及用户详细信息	
		try {
			userService.updateUser(user); 
			userService.updateUserprofile(userProfile);
		}catch(Exception e) {
			return Result.fail("账号["+user.getUsername()+"]更新失败，请重新尝试！！");
		}			
	    return Result.SUCCESS();
	}
	
	/**
	 * 用户配置
	 */
	@RequestMapping(value="/getusers",method= RequestMethod.GET)
	public Result getusers(int limit,int page,String sort,String sortOrder,String begintime,String endtime,String username) {
		//检查必要参数是否存在
		PageInfo<User> pageinfo;
		try {
			pageinfo = userService.getuser(page, limit,sort, sortOrder, begintime, endtime, username);
			if(pageinfo==null || pageinfo.getTotal()<=0) {	
				return Result.fail(0);
			}
		} catch (ParseException e) {
			return Result.fail(0);
		}	
		//循环将密码设置为10个*
		List<User> users=pageinfo.getList();
		for(User u: users) {
			u.setPassword("**********");
		}
		return Result.SUCCESS(users,(int)pageinfo.getTotal());
	}
	
	@RequestMapping(value="/edituser",method= RequestMethod.PUT)
	private Result edit(@RequestBody User user) {
		if(user==null) {
			return Result.of(ResultEnum.Parameter_Is_Empty);
		}		
		String username=user.getUsername();
		String password=user.getPassword();
		//检查用户名和密码是否为空
		 if(StringUtils.isNotBlank(username)) {
			 Result.fail("账号为空更新失败，请重新尝试！！");
		 }
		 if(StringUtils.isNotBlank(password)) {
			 Result.fail("密码为空更新失败，请重新尝试！！"); 
		 }
		 				 	
		if(userService.isExitsUser(username)){
			//更新
			//检查密码是否为**********,否则将密码加密
			 if(!password.equals("**********")) {
				//对密码加密			
				user.setPassword(encoder.encode(password));	
			 }
			try {
				userService.updateUser(user); 
				}catch(Exception e) {
					return Result.fail("账号["+user.getUsername()+"]更新失败，请重新尝试！！");
				}
		}else {
			//注册
			try {
				//对密码加密				
				user.setPassword(encoder.encode(password));		
				//设置日期
				user.setCreatetime(new Date());
				//创建userprofile
				Userprofile userprofile=new Userprofile();
				userprofile.setUsername(username);
				userprofile.setHeadsculpture(user.getSex()==0?"/images/girl.jpg":"/images/boy.jpg");
				//插入记录
				userService.saveUser(user);
				userService.saveUserprofile(userprofile); 				
			}catch(Exception e) {
				return Result.fail("账号["+username+"]注册失败，请重新尝试！！");
			}
		}	
	    return Result.SUCCESS();
	}
	
	@RequestMapping(value="/deleteuser/{username}",method= RequestMethod.DELETE)
	private Result del(@PathVariable String username) {
		try {
			userService.deleteUser(username); 
			}catch(Exception e) {
				return Result.fail("账号["+username+"]删除失败，请重新尝试！！");
			}
		    return Result.SUCCESS();
	}
	
}
