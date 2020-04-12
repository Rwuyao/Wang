package org.datacenter.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.datacenter.mapper.ResourceMapper;
import org.datacenter.model.Resource;
import org.datacenter.model.ResourceExample;
import org.datacenter.model.Role;
import org.datacenter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class ResourceService {

	 @Autowired ResourceMapper resourceMapper;
	   
	 //未点击菜单树时，查询需进行分页处理
	 public PageInfo<Resource> getAllResource(int Page,int pagesize,String sort,String sortOrder){
		 PageHelper.startPage(Page, pagesize);	
		 ResourceExample example= new ResourceExample();		 
		 if(StringUtils.isNotBlank(sort)) {
			 example.setOrderByClause(sort +" " +sortOrder); 
		 }	
		 List<Resource> rs=resourceMapper.selectByExample(example);		
		 PageInfo<Resource> pageInfo = new PageInfo<Resource>(rs);
		 return pageInfo;
	 }
	 
	 //点击菜单树后，直接返回所有菜单，再根据节点id进行筛选
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
	 
	 public boolean isExitsResource(Integer id) {
		if(id==null) {
			return false;
		}
		 ResourceExample example= new ResourceExample();
		 example.createCriteria().andIdEqualTo(id);    	
	    	long count=resourceMapper.countByExample(example);
	    	if(count>0) {
	    		return true;
	    	}else {	    		
	    		return false;
	    	}
	    }
	 
	 public void updateResource(Resource resource) {
		 ResourceExample example= new ResourceExample();
		 example.createCriteria().andIdEqualTo(resource.getId()); 		 			
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
