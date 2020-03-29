package org.datacenter.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.datacenter.mapper.UserMapper;
import org.datacenter.mapper.UserRoleMapper;
import org.datacenter.model.User;
import org.datacenter.model.UserExample;
import org.datacenter.model.UserExample.Criteria;
import org.datacenter.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;


@Service
public class UserService {

	 @Autowired UserMapper userMapper;
	    
	 @Autowired UserRoleMapper userRoleMapper;
	 
	 public User findByUserName(String username){
	        return userMapper.findByUserName(username);
	    }
	 
	 public List<User> getuser(int Page,int pagesize,String sort,String sortOrder,String begintime,String endtime,String username) throws ParseException{
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
		 return userList;
	 }
	 
	    
	 public boolean isExits(String username) {
	    	UserExample example =new UserExample();
	    	example.createCriteria().andUsernameEqualTo(username);	    	
	    	long count=userMapper.countByExample(example);
	    	if(count>0) {
	    		return true;
	    	}else {	    		
	    		return false;
	    	}
	    }
	 
	 public void update(User user) {
		 UserExample example =new UserExample();
		 example.createCriteria().andUsernameEqualTo(user.getUsername());			
		 userMapper.updateByExampleSelective(user, example);
	 }
	 
	 @Transactional
	 public void insert(User user) {
		 //注册用户
		 userMapper.insert(user);
		 //绑定基本用户角色
		 userRoleMapper.insert(new UserRole(user.getUsername(),"ROLE_USER"));
	 }
	 
	 public void delete(String username) {
		//删除用户
		UserExample example =new UserExample();
	    example.createCriteria().andUsernameEqualTo(username);
		 userMapper.deleteByExample(example);
		 //删除用户角色关联
	 }
	 
}
