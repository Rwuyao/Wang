package org.datacenter.service;

import org.datacenter.mapper.UserMapper;
import org.datacenter.model.User;
import org.datacenter.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	 @Autowired UserMapper userMapper;
	    
	 public User findByUserName(String username){
	        return userMapper.findByUserName(username);
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
	 
	 public void insert(User user) {		 
		 userMapper.insert(user);
	 }
	 
	 public void delete(String username) {
		 UserExample example =new UserExample();
	    example.createCriteria().andUsernameEqualTo(username);
		 userMapper.deleteByExample(example);
	 }
	 
}
