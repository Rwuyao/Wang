package org.datacenter.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.datacenter.mapper.UserMapper;
import org.datacenter.mapper.UserRoleMapper;
import org.datacenter.mapper.UserprofileMapper;
import org.datacenter.model.Resource;
import org.datacenter.model.Role;
import org.datacenter.model.User;
import org.datacenter.model.UserExample;
import org.datacenter.model.UserExample.Criteria;
import org.datacenter.model.UserRole;
import org.datacenter.model.UserRoleExample;
import org.datacenter.model.Userprofile;
import org.datacenter.model.UserprofileExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class UserService {

	 @Autowired UserMapper userMapper;
	    
	 @Autowired UserRoleMapper userRoleMapper;
	
	 @Autowired UserprofileMapper userprofileMapper;
	 	 
	 public void updateUserprofile(Userprofile userprofile) {
		 UserprofileExample example =new UserprofileExample();
		 example.createCriteria().andUsernameEqualTo(userprofile.getUsername());			
		 userprofileMapper.updateByExampleSelective(userprofile, example);
	 }
	 
	 public Userprofile getUserprofile(String username) {		 		
		 Userprofile userprofile=userprofileMapper.findByUserName(username); 
		 return userprofile;
	 }
	 
	 public void saveUserprofile(Userprofile userprofile) {
		 userprofileMapper.insert(userprofile);
	 }
	 
	 public long countUserRole(String username) {
		 	UserRoleExample example =new UserRoleExample();
	    	example.createCriteria().andUsernameEqualTo(username);	    	
	    	long count=userRoleMapper.countByExample(example);
	    	return count;
	 }
	 
	 public PageInfo<Role>  getuserRole(int Page,int pagesize,String sort,String sortOrder,String username) {
		 PageHelper.startPage(Page, pagesize);		 	 
		 List<Role> List=userMapper.getRoleByUserName(username);		 
		 PageInfo<Role> pageInfo = new PageInfo<Role>(List);
		 return pageInfo;
	 }
	 
	 public void saveUserRole(UserRole userRole) {
		 userRoleMapper.insert(userRole); 
	 }
	 
	 public void deleteUserRole(UserRole userRole) {
		//删除用户角色关联
		 	UserRoleExample example =new UserRoleExample();
		    example.createCriteria()
		    .andUsernameEqualTo(userRole.getUsername())
		    .andRolenameEqualTo(userRole.getRolename());		   
		    userRoleMapper.deleteByExample(example);
			 
		 }
	 
	 public boolean isExitsUserRole(String username,String rolename) {
		 UserRoleExample example =new UserRoleExample();
	    	example.createCriteria().andUsernameEqualTo(username).andRolenameEqualTo(rolename);	    	
	    	long count=userRoleMapper.countByExample(example);
	    	if(count>0) {
	    		return true;
	    	}else {	    		
	    		return false;
	    	}
	 }
	 
	 
	 public User findByUserName(String username){
	        return userMapper.findByUserName(username);
	   }
	 
	 public PageInfo<User> getuser(int Page,int pagesize,String sort,String sortOrder,String begintime,String endtime,String username) throws ParseException{
		 PageHelper.startPage(Page, pagesize);		 
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
	        		 
		 UserExample example =new UserExample();
		 Criteria criteria =example.createCriteria();
		 
		 if(StringUtils.isNotBlank(sort)) {
			 example.setOrderByClause(sort +" " +sortOrder); 
		 }	
		 if(StringUtils.isNotBlank(begintime)) {
			 criteria.andCreatetimeGreaterThanOrEqualTo(simpleDateFormat.parse(begintime)) ;
		 }
		 if(StringUtils.isNotBlank(endtime)) {
			 criteria.andCreatetimeLessThanOrEqualTo(simpleDateFormat.parse(endtime));
		 }
		 if(StringUtils.isNotBlank(username)) {
			 criteria.andUsernameEqualTo(username);
		 }	 
		 List<User> userList=userMapper.selectByExample(example);
		 PageInfo<User> pageInfo = new PageInfo<User>(userList);
		 return pageInfo;
	 }
	 
	    
	 public boolean isExitsUser(String username) {
	    	UserExample example =new UserExample();
	    	example.createCriteria().andUsernameEqualTo(username);	    	
	    	long count=userMapper.countByExample(example);
	    	if(count>0) {
	    		return true;
	    	}else {	    		
	    		return false;
	    	}
	    }
	 
	 public void updateUser(User user) {
		 UserExample example =new UserExample();
		 example.createCriteria().andUsernameEqualTo(user.getUsername());			
		 userMapper.updateByExampleSelective(user, example);
	 }
	 
	 @Transactional
	 public void saveUser(User user) {
		 //注册用户
		 userMapper.insert(user);
		 //绑定基本用户角色
		 userRoleMapper.insert(new UserRole(user.getUsername(),"ROLE_USER"));
	 }
	 
	 public void deleteUser(String username) {
		//删除用户
		UserExample example =new UserExample();
	    example.createCriteria().andUsernameEqualTo(username);
		 userMapper.deleteByExample(example);
		 //删除用户角色关联
	 }
	 
}
