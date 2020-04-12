package org.datacenter.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.datacenter.mapper.RoleMapper;
import org.datacenter.mapper.UserMapper;
import org.datacenter.model.Resource;
import org.datacenter.model.ResourceExample;
import org.datacenter.model.Role;
import org.datacenter.model.RoleExample;
import org.datacenter.model.RoleExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RoleService {

	 @Autowired RoleMapper roleMapper;
	    
	 public List<Role> findRoleByRoleName(List<String> rolename){
	        return roleMapper.findRoleByRoleName(rolename);
	    }
	 
	 public PageInfo<Role> getrole(int Page,int pagesize,String sort,String sortOrder,String rolename) {
		 PageHelper.startPage(Page, pagesize);		 		 
		 RoleExample example =new RoleExample();
		 Criteria criteria =example.createCriteria();
		 
		 if(StringUtils.isNotBlank(rolename)) {
			 criteria.andRolenameEqualTo(rolename);
		 }	
		 if(StringUtils.isNotBlank(sort)) {
			 example.setOrderByClause(sort +" " +sortOrder); 
		 }	
		  
		 List<Role> roleList=roleMapper.selectByExample(example);
		 PageInfo<Role> pageInfo = new PageInfo<Role>(roleList);
		 return pageInfo;
	 }
	 
	 public boolean isExits(String rolename) {	    			 
		 RoleExample example =new RoleExample();
	    	example.createCriteria().andRolenameEqualTo(rolename); 	    	
	    	long count=roleMapper.countByExample(example);
	    	if(count>0) {
	    		return true;
	    	}else {	    		
	    		return false;
	    	}
	    }
	 	 
	 public void updateRole(Role role) {
		 RoleExample example= new RoleExample();
		 example.createCriteria().andRolenameEqualTo(role.getRolename()) ; 		 			
		 roleMapper.updateByExampleSelective(role, example);
	 }
	 
	 public void saveRole(Role role) {		
		 role.setCreatetime(new Date());
		 roleMapper.insert(role);
	 }
	 
	 public void deleteRole(String Rolename) {
		//删除用户
		 RoleExample example= new RoleExample();
		 example.createCriteria().andRolenameEqualTo(Rolename);  
		 roleMapper.deleteByExample(example);
	 }
 
}
