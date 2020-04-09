package org.datacenter.service;

import java.util.List;
import org.datacenter.mapper.ResourceMapper;
import org.datacenter.model.Resource;
import org.datacenter.model.ResourceExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ResourceService {

	 @Autowired ResourceMapper resourceMapper;
	   
	 /*
		 * public List<User> getuser(int Page,int pagesize,String sort,String
		 * sortOrder,String begintime,String endtime,String username) throws
		 * ParseException{ PageHelper.startPage(Page, pagesize); SimpleDateFormat
		 * simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
		 * 
		 * UserExample example =new UserExample(); Criteria criteria
		 * =example.createCriteria();
		 * 
		 * if(StringUtils.isNotBlank(sort)) { example.setOrderByClause(sort +" "
		 * +sortOrder); } if(StringUtils.isNotBlank(begintime)) {
		 * criteria.andCreatetimeGreaterThanOrEqualTo(simpleDateFormat.parse(begintime))
		 * ; } if(StringUtils.isNotBlank(endtime)) {
		 * criteria.andCreatetimeLessThanOrEqualTo(simpleDateFormat.parse(endtime)); }
		 * if(StringUtils.isNotBlank(username)) { criteria.andUsernameEqualTo(username);
		 * } List<User> userList=userMapper.selectByExample(example); return userList; }
		 */
	 
	 public List<Resource> getAllResource(){
		 ResourceExample example= new ResourceExample();
		 return resourceMapper.selectByExample(example);	
	 }
	 
	 
	 public List<Resource> getByResourceName(String ResourceName){
		 ResourceExample example= new ResourceExample();
		 example.createCriteria().andResourceEqualTo(ResourceName);
		 return resourceMapper.selectByExample(example);		 
	   }
	   
	 public boolean isExitsResource(int id) {
		 ResourceExample example= new ResourceExample();
		 example.createCriteria().andIdEqualTo(id);    	
	    	long count=resourceMapper.countByExample(example);
	    	if(count>0) {
	    		return true;
	    	}else {	    		
	    		return false;
	    	}
	    }
	 
	 public boolean isExitsResource(String ResourceName) {
		 ResourceExample example= new ResourceExample();
		 example.createCriteria().andResourceEqualTo(ResourceName);    	
	    	long count=resourceMapper.countByExample(example);
	    	if(count>0) {
	    		return true;
	    	}else {	    		
	    		return false;
	    	}
	    }
	 
	 public void updateResource(Resource resource) {
		 ResourceExample example= new ResourceExample();
		 example.createCriteria().andResourceEqualTo(resource.getResource()); 		 			
		 resourceMapper.updateByExampleSelective(resource, example);
	 }
	 
	 public void saveResource(Resource resource) {
		 resourceMapper.insert(resource);
	 }
	 
	 public void deleteResource(String ResourceName) {
		//删除用户
		 ResourceExample example= new ResourceExample();
		 example.createCriteria().andResourceEqualTo(ResourceName);  
		 resourceMapper.deleteByExample(example);
	 }
	 
	 public void deleteResource(Integer id) {
			//删除用户
			 ResourceExample example= new ResourceExample();
			 example.createCriteria().andIdEqualTo(id);   
			 resourceMapper.deleteByExample(example);
		 }
}
