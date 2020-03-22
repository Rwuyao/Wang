package org.datacenter.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.datacenter.mapper.UserMapper;
import org.datacenter.model.User;
import org.datacenter.model.UserExample;
import org.datacenter.model.UserExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;


@Service
public class UserService {

	 @Autowired UserMapper userMapper;
	    
	 public User findByUserName(String username){
	        return userMapper.findByUserName(username);
	    }
	 
	 public List<User> getuser(int Page,int pagesize){
		 PageHelper.startPage(Page, pagesize);		 
		 UserExample example =new UserExample(); 
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
	 
	 public void insert(User user) {		 
		 userMapper.insert(user);
	 }
	 
	 public void delete(String username) {
		 UserExample example =new UserExample();
	    example.createCriteria().andUsernameEqualTo(username);
		 userMapper.deleteByExample(example);
	 }
	 
}
