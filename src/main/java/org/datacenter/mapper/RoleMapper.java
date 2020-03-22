package org.datacenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.datacenter.model.Role;
import org.datacenter.model.RoleExample;

@Mapper
public interface  RoleMapper {

	List<Role> findRoleByRoleName(List<String> list); 
	
	 long countByExample(RoleExample example);

	    int deleteByExample(RoleExample example);

	    int deleteByPrimaryKey(Integer id);

	    int insert(Role record);

	    int insertSelective(Role record);

	    List<Role> selectByExample(RoleExample example);

	    Role selectByPrimaryKey(Integer id);

	    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

	    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

	    int updateByPrimaryKeySelective(Role record);

	    int updateByPrimaryKey(Role record);
}
